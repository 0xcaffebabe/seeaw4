package wang.ismy.seeaw4.server.message.chain;

import com.google.gson.Gson;
import wang.ismy.seeaw4.common.ExecuteService;
import wang.ismy.seeaw4.common.command.CommandHandler;
import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.message.chain.MessageChain;
import wang.ismy.seeaw4.common.message.chain.impl.PromiseMessageChain;
import wang.ismy.seeaw4.common.message.impl.CommandMessage;

import wang.ismy.seeaw4.server.command.ServerCommandHandler;
import wang.ismy.seeaw4.server.netty.NettyConnectionService;

import java.io.IOException;


/**
 * @author MY
 * @date 2019/12/14 17:42
 */
public class ServerMessageChain implements MessageChain {

    private ExecuteService executeService = ExecuteService.getInstance();
    private NettyConnectionService connectionService = NettyConnectionService.getInstance();
    private CommandHandler commandHandler = new ServerCommandHandler();

    @Override
    public void process(Connection connection, Message message) throws IOException {
        executeService.excute(() -> {
            if (message instanceof CommandMessage) {
                Message resultMessage;
                resultMessage = commandHandler.process(connection, (CommandMessage) message);
                if (resultMessage != null){
                    // 如果发来的消息有回调ID，那么发回给他的消息带上这个ID
                    Object o = message.addition().get(PromiseMessageChain.PROMISE_CALLBACK);
                    if (o != null){
                        resultMessage.addition().put(PromiseMessageChain.PROMISE_CALLBACK,o.toString());
                    }
                    try {
                        connection.sendMessage(resultMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }
}
