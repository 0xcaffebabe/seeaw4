package wang.ismy.seeaw4.server.message.chain;

import wang.ismy.seeaw4.common.ExecuteService;
import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.message.chain.MessageChain;
import wang.ismy.seeaw4.common.message.impl.CommandMessage;
import wang.ismy.seeaw4.common.message.impl.TextMessage;

import java.io.IOException;

/**
 * @author MY
 * @date 2019/12/14 17:42
 */
public class ServerMessageChain implements MessageChain {

    private ExecuteService executeService = ExecuteService.getInstance();

    @Override
    public void process(Connection connection, Message message) throws IOException {
        executeService.excute(() -> {
            if (message instanceof CommandMessage) {
                TextMessage textMessage = new TextMessage("cmd receive");
                try {
                    connection.sendMessage(textMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
