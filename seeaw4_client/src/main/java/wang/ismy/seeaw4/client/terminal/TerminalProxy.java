package wang.ismy.seeaw4.client.terminal;

import lombok.extern.slf4j.Slf4j;
import wang.ismy.seeaw4.client.ClientService;
import wang.ismy.seeaw4.common.command.CommandKey;
import wang.ismy.seeaw4.common.command.CommandType;
import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.message.impl.CommandMessage;
import wang.ismy.seeaw4.common.message.impl.TextMessage;
import wang.ismy.seeaw4.terminal.Terminal;
import wang.ismy.seeaw4.terminal.camera.Camera;
import wang.ismy.seeaw4.terminal.desktop.Desktop;

import java.io.IOException;

/**
 * 远程终端代理，可以通过此对象，访问远程终端
 * 以及远程终端可以回调此对象
 * @author my
 */
@Slf4j
public class TerminalProxy extends Terminal {

    private Connection connection;
    private ClientService clientService;
    private String remoteClientId;
    private String selfId;

    public TerminalProxy(Connection connection,String remoteClientId,String selfId) throws IOException {
        if (connection == null){
            throw new IllegalStateException("connection 不得为null");
        }
        this.connection = connection;
        this.remoteClientId = remoteClientId;
        this.selfId = selfId;
        this.clientService = new ClientService(connection);
        bind();
    }

    private void bind() throws IOException{
        CommandMessage cmd = new CommandMessage();
        cmd.setCommand(CommandType.SHELL_BIND);
        cmd.addAddition(CommandKey.PER_ID,remoteClientId);
        cmd.addAddition(CommandKey.SELF_ID,selfId);
        clientService.sendCallbackMessage(cmd,(conn,msg)->{
            if (msg instanceof TextMessage){
                String text = ((TextMessage) msg).getText();
                if ("绑定成功".equals(text)){
                    bindComplete();
                }
            }
        });
    }

    private void bindComplete(){
        log.info("终端绑定成功，remote:{}",remoteClientId);
    }

    @Override
    public void input(String cmd) throws IOException {
        CommandMessage cmdMsg = new CommandMessage();
        cmdMsg.setCommand(CommandType.SHELL_CMD);
        cmdMsg.setPayload(cmd.getBytes());
        cmdMsg.addAddition(CommandKey.PER_ID,remoteClientId);
        cmdMsg.addAddition(CommandKey.SELF_ID,selfId);
        connection.sendMessage(cmdMsg);
    }

    @Override
    public Camera getCamera() {
        return null;
    }

    @Override
    public Desktop getDesktop() {
        return null;
    }
}
