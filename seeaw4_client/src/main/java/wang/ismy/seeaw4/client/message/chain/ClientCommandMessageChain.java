package wang.ismy.seeaw4.client.message.chain;

import wang.ismy.seeaw4.client.command.ClientCommandHandler;
import wang.ismy.seeaw4.common.ExecuteService;
import wang.ismy.seeaw4.common.command.CommandHandler;
import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.message.chain.MessageChain;
import wang.ismy.seeaw4.common.message.chain.impl.PromiseMessageChain;
import wang.ismy.seeaw4.common.message.impl.CommandMessage;
import wang.ismy.seeaw4.terminal.Terminal;

import java.io.IOException;

/**
 * @author MY
 * @date 2019/12/17 16:38
 */
public class ClientCommandMessageChain implements MessageChain {
    private ExecuteService executeService = ExecuteService.getInstance();
    private CommandHandler commandHandler;
    private Terminal terminal;
    public ClientCommandMessageChain(Terminal terminal){
        this.terminal = terminal;
        commandHandler = new ClientCommandHandler(terminal);
    }

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
