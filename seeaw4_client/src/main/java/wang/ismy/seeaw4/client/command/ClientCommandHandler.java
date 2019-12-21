package wang.ismy.seeaw4.client.command;

import com.google.gson.reflect.TypeToken;
import wang.ismy.seeaw4.client.Client;
import wang.ismy.seeaw4.client.client.LocalPer;
import wang.ismy.seeaw4.client.terminal.TerminalProxy;
import wang.ismy.seeaw4.common.client.Per;
import wang.ismy.seeaw4.common.command.CommandHandler;
import wang.ismy.seeaw4.common.command.CommandKey;
import wang.ismy.seeaw4.common.command.CommandType;
import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.message.impl.CommandMessage;
import wang.ismy.seeaw4.common.message.impl.ImgMessage;
import wang.ismy.seeaw4.common.message.impl.TextMessage;
import wang.ismy.seeaw4.common.utils.JsonUtils;
import wang.ismy.seeaw4.terminal.Resolution;
import wang.ismy.seeaw4.terminal.Terminal;
import wang.ismy.seeaw4.terminal.enums.ImgType;
import wang.ismy.seeaw4.terminal.observer.impl.LazyTerminalObserver;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2019/12/17 16:44
 */
public class ClientCommandHandler implements CommandHandler {

    private Terminal terminal;
    private List<LocalPer> localPerList;
    private Client client;

    public ClientCommandHandler(Terminal terminal, List<LocalPer> localPerList, Client client) {
        this.terminal = terminal;
        this.localPerList = localPerList;
        this.client = client;
    }

    @Override
    public Message process(Connection connection, CommandMessage commandMessage) {
        switch (commandMessage.getCommand()){
            case LIST_CLIENT:
                return clientList(connection, commandMessage);
            case SCREEN:
                return screen(connection,commandMessage);
            case PHOTO:
                return photo(connection,commandMessage);
            case SHELL_BUFFER:
                return shellBuffer(connection, commandMessage);
            case SHELL_BIND:
                return shellBind(connection,commandMessage);
            case SHELL_RECEIVE:
                // 通知终端代理
                Object o = commandMessage.getAddition(CommandKey.PER_ID);
                if (o != null) {
                    String perId = o.toString();
                    LocalPer localPer = localPerList.stream().filter(l -> l.getId().equals(perId)).collect(Collectors.toList()).get(0);
                    localPer.getTerminalProxy().onMessage(new String(commandMessage.getPayload()));
                }
                return null;
            case SHELL_CMD:
                try {
                    terminal.input(new String(commandMessage.getPayload()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            default:
                return null;
        }
    }

    private Message shellBind(Connection connection, CommandMessage commandMessage) {

         // o如果存在，则就是主控方的ID
        Object o = commandMessage.addition().get(CommandKey.PER_ID);
        if (o != null){
            String perId = o.toString();
            terminal.registerObserver(new LazyTerminalObserver() {
                @Override
                public void onMessage(String msg) {
                    try {
                        CommandMessage cmd = new CommandMessage();
                        cmd.setCommand(CommandType.SHELL_RECEIVE);
                        cmd.addition().put(CommandKey.PER_ID,perId);
                        cmd.setPayload(msg.getBytes());
                        connection.sendMessage(cmd);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return new TextMessage("绑定成功");
        }
        return null;
    }

    private Message screen(Connection connection,CommandMessage message){
        byte[] screen = terminal.getDesktop().getScreen(ImgType.JPEG, new Resolution(1366,768));
        return new ImgMessage(screen,ImgType.JPEG.getFormat());
    }

    private Message photo(Connection connection,CommandMessage message){
        byte[] screen = terminal.getCamera().getCameraSnapshot(ImgType.JPEG, new Resolution(640,480));
        return new ImgMessage(screen,ImgType.JPEG.getFormat());
    }

    private Message shellBuffer(Connection connection,CommandMessage message){

        return new TextMessage(terminal.getTerminalBuffer());
    }

    private Message clientList(Connection connection,CommandMessage commandMessage){
        String str = new String(commandMessage.getPayload());
        List<Per> list = JsonUtils.fromJson(str, new TypeToken<List<Per>>() {
        }.getType());;
        client.onClientListChange(list);
        return null;
    }
}
