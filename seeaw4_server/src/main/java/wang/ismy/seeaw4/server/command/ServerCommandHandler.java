package wang.ismy.seeaw4.server.command;

import wang.ismy.seeaw4.common.client.Per;
import wang.ismy.seeaw4.common.command.CommandHandler;
import wang.ismy.seeaw4.common.command.CommandKey;
import wang.ismy.seeaw4.common.command.CommandType;
import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.message.chain.impl.PromiseMessageChain;
import wang.ismy.seeaw4.common.message.impl.CommandMessage;
import wang.ismy.seeaw4.common.message.impl.TextMessage;
import wang.ismy.seeaw4.common.promise.ConnectionPromise;
import wang.ismy.seeaw4.common.utils.JsonUtils;
import wang.ismy.seeaw4.server.netty.NettyConnectionService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2019/12/17 10:12
 */
public class ServerCommandHandler implements CommandHandler {

    private final NettyConnectionService connectionService = NettyConnectionService.getInstance();

    @Override
    public Message process(Connection connection, CommandMessage commandMessage) {

        switch (commandMessage.getCommand()) {
            case LIST_CLIENT:
                return getClientList(connection, commandMessage);
            // 服务端如果接收到screen请求，那服务端要去发起客户端指定的另一个客户端的screen请求
            default:
                return sendMsgAndCallback(connection,commandMessage,commandMessage.getCommand());
        }
    }

    /**
     * 向commandMessage指定的客户端发送一条控制消息
     * 并且等待指定的客户端返回消息将返回的消息发送给connection
     * @param connection
     * @param commandMessage
     * @return
     */
    private Message sendMsgAndCallback(Connection connection, CommandMessage commandMessage,CommandType commandType) {
        // connection 是主控方
        // success回调中的conn是被控方
        // 所以需要把被控方发来的消息发送给主控方
        Object o = commandMessage.addition().get(CommandKey.PER_ID);
        if (o == null) {
            return null;
        }
        String perId = o.toString();
        CommandMessage clientCmd = new CommandMessage();
        clientCmd.setCommand(commandType);
        //　如果发来的消息带有self-id,那么需要把self-id转为 per-id带上
        clientCmd.addition().put(CommandKey.PER_ID,commandMessage.addition().get(CommandKey.SELF_ID));
        // 如果发来的消息带有载荷，则也附带上
        clientCmd.setPayload(commandMessage.getPayload());
        new ConnectionPromise(clientCmd)
                .success((conn, msg) -> {
                    // 获取主控方的回调id
                    Object o1 = commandMessage.addition().get(PromiseMessageChain.PROMISE_CALLBACK);
                    String callbackId = null;
                    if (o1 != null) {
                        callbackId = o1.toString();
                    }
                    // 将回调id包装进一条消息，发送给主控方
                    msg.addition().put(PromiseMessageChain.PROMISE_CALLBACK, callbackId);
                    // 同时也需要把被控方ID包装进消息，发送给主控方
                    msg.addition().put(CommandKey.PER_ID, perId);
                    try {
                        connection.sendMessage(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).async();
        // 根据ID获取被控方连接
        Connection conn = connectionService.getConnectionById(perId);
        if (conn == null) {
            return new TextMessage("被控方不在线");
        }
        try {
            conn.sendMessage(clientCmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Message getClientList(Connection connection, CommandMessage message) {
        List<Per> ret = connectionService.getConnectionList()
                .stream()
                .map(conn->{
                    Per per = Per.convert(conn);
                    if (conn.equals(connection)){
                        per.setSelf(true);
                    }
                    return per;
                })
                .collect(Collectors.toList());

        return new TextMessage(JsonUtils.toJson(ret));
    }
}
