package wang.ismy.seeaw4.server.message.chain;

import com.google.gson.Gson;
import wang.ismy.seeaw4.common.ExecuteService;
import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.message.chain.MessageChain;
import wang.ismy.seeaw4.common.message.chain.impl.PromiseMessageChain;
import wang.ismy.seeaw4.common.message.impl.CommandMessage;
import wang.ismy.seeaw4.common.message.impl.TextMessage;
import wang.ismy.seeaw4.server.netty.NettyConnectionService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2019/12/14 17:42
 */
public class ServerMessageChain implements MessageChain {

    private ExecuteService executeService = ExecuteService.getInstance();
    private NettyConnectionService connectionService = NettyConnectionService.getInstance();

    @Override
    public void process(Connection connection, Message message) throws IOException {
        executeService.excute(() -> {
            if (message instanceof CommandMessage) {
                TextMessage textMessage ;
                switch (((CommandMessage) message).getCommand()){
                    case "list-client":
                        List<String> connectionList = connectionService.getConnectionList()
                                .stream()
                                .map(conn -> conn.getInfo().getSocketAddress().toString())
                                .collect(Collectors.toList());
                        textMessage = new TextMessage(new Gson().toJson(connectionList));
                        break;
                    default:
                        textMessage = new TextMessage("unknown cmd");
                }
                // 如果发来的消息有回调ID，那么发回给他的消息带上这个ID
                Object o = message.addition().get(PromiseMessageChain.PROMISE_CALLBACK);
                if (o != null){
                    textMessage.addition().put(PromiseMessageChain.PROMISE_CALLBACK,o.toString());
                }
                try {
                    connection.sendMessage(textMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
