package wang.ismy.seeaw4.client.terminal;

import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import wang.ismy.seeaw4.client.ClientService;
import wang.ismy.seeaw4.common.ExecuteService;
import wang.ismy.seeaw4.common.command.CommandKey;
import wang.ismy.seeaw4.common.command.CommandType;
import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.message.impl.CommandMessage;
import wang.ismy.seeaw4.common.message.impl.ImgMessage;
import wang.ismy.seeaw4.common.message.impl.TextMessage;
import wang.ismy.seeaw4.common.promise.ConnectionPromise;
import wang.ismy.seeaw4.common.utils.BytesUtils;
import wang.ismy.seeaw4.common.utils.JsonUtils;
import wang.ismy.seeaw4.terminal.Resolution;
import wang.ismy.seeaw4.terminal.Terminal;
import wang.ismy.seeaw4.terminal.TerminalBuffer;
import wang.ismy.seeaw4.terminal.camera.Camera;
import wang.ismy.seeaw4.terminal.desktop.Desktop;
import wang.ismy.seeaw4.terminal.enums.ImgType;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

/**
 * 远程终端代理，可以通过此对象，访问远程终端
 * 以及远程终端可以回调此对象
 *
 * @author my
 */
@Slf4j
public class TerminalProxy extends Terminal {

    private Connection connection;
    private ClientService clientService;
    private String remoteClientId;
    private String selfId;
    private Runnable bindSuccessListener;
    private ExecuteService executeService = ExecuteService.getInstance();
    private Map<String,Object> systemInfo ;

    /**
     * 该构造器调用后会自动绑定
     *
     * @param connection
     * @param remoteClientId
     * @param selfId
     * @throws IOException
     */
    public TerminalProxy(Connection connection, String remoteClientId, String selfId) throws IOException {
        if (connection == null) {
            throw new IllegalStateException("connection 不得为null");
        }
        this.connection = connection;
        this.remoteClientId = remoteClientId;
        this.selfId = selfId;
        this.clientService = new ClientService(connection);
        bind();
    }

    public TerminalProxy() { }

    public void setConnection(Connection connection) {
        this.connection = connection;
        this.clientService = new ClientService(connection);
    }

    public void setRemoteClientId(String remoteClientId) {
        this.remoteClientId = remoteClientId;
    }

    public void setSelfId(String selfId) {
        this.selfId = selfId;
    }

    @Override
    public void onMessage(String msg) {
        super.onMessage(msg);
    }

    public void bind() throws IOException {
        CommandMessage cmd = new CommandMessage();
        cmd.setCommand(CommandType.SHELL_BIND);
        cmd.addAddition(CommandKey.PER_ID, remoteClientId);
        cmd.addAddition(CommandKey.SELF_ID, selfId);
        clientService.sendCallbackMessage(cmd, (conn, msg) -> {
            if (msg instanceof TextMessage) {
                String text = ((TextMessage) msg).getText();
                if ("绑定成功".equals(text)) {
                    bindComplete();
                }
            }
        });
    }

    private void bindComplete() {
        log.info("终端绑定成功，remote:{}", remoteClientId);
        // 连接成功刷新terminal buffer
        refreshTerminalBuffer();
        // 通知监听者
        if (bindSuccessListener != null){
            executeService.excute(()->{
                bindSuccessListener.run();
            });
        }
    }

    public void setBindSuccessListener(Runnable listener){
        this.bindSuccessListener = listener;
    }



    @Override
    public void input(String cmd) throws IOException {
        CommandMessage cmdMsg = new CommandMessage();
        cmdMsg.setCommand(CommandType.SHELL_CMD);
        cmdMsg.setPayload(cmd.getBytes());
        cmdMsg.addAddition(CommandKey.PER_ID, remoteClientId);
        cmdMsg.addAddition(CommandKey.SELF_ID, selfId);
        connection.sendMessage(cmdMsg);
    }

    @Override
    public Camera getCamera() {
        class RemoteCamera implements Camera{
            private byte[] bytes;
            @Override
            public byte[] getCameraSnapshot(ImgType type, Resolution resolution) {
                final CountDownLatch latch = new CountDownLatch(1);
                CommandMessage cmd = new CommandMessage();
                cmd.setCommand(CommandType.PHOTO);
                cmd.addAddition(CommandKey.PER_ID,remoteClientId);
                new ConnectionPromise(cmd)
                        .success((conn,msg)->{
                                if (msg instanceof ImgMessage){
                                    log.info("remote camera 接收到数据,{}",msg);
                                    bytes= msg.getPayload();
                                }
                                log.info("lock count");
                                latch.countDown();
                        }).async();
                try {
                    connection.sendMessage(cmd);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                log.info("camera lock");
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("camera unlock");
                return bytes;
            }
        }
        return new RemoteCamera();
    }

    @Override
    public Desktop getDesktop() {
        class  RemoteDesktop implements Desktop{
            private byte[] bytes;
            @Override
            public byte[] getScreen(ImgType imgType, Resolution resolution) {
                final CountDownLatch latch = new CountDownLatch(1);
                CommandMessage cmd = new CommandMessage();
                cmd.setCommand(CommandType.SCREEN);
                cmd.addAddition(CommandKey.PER_ID,remoteClientId);
                new ConnectionPromise(cmd)
                        .success((conn,msg)->{
                            if (msg instanceof ImgMessage){
                                log.info("remote desktop 接收到数据,{}",msg);
                                bytes= msg.getPayload();
                            }
                            log.info("lock count");
                            latch.countDown();
                        }).async();
                try {
                    connection.sendMessage(cmd);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                log.info("desktop lock");
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("desktop unlock");
                return bytes;
            }
        }
        return new RemoteDesktop();
    }

    @Override
    public String getTerminalBuffer() {
        final  CountDownLatch latch = new CountDownLatch(1);
        CommandMessage cmd = new CommandMessage();
        cmd.setCommand(CommandType.SHELL_BUFFER);
        cmd.addAddition(CommandKey.PER_ID, remoteClientId);
        new ConnectionPromise(cmd)
                .success((conn, msg) -> {
                    if (msg instanceof TextMessage) {
                        terminalBuffer = new TerminalBuffer(1024);
                        terminalBuffer.append(((TextMessage) msg).getText());
                    }
                    latch.countDown();
                }).async();

        try {
            connection.sendMessage(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return terminalBuffer.getBuffer();
    }

    public  void refreshTerminalBuffer() {
        CommandMessage cmd = new CommandMessage();
        cmd.setCommand(CommandType.SHELL_BUFFER);
        cmd.addAddition(CommandKey.PER_ID, remoteClientId);
        new ConnectionPromise(cmd)
                .success((conn, msg) -> {
                    if (msg instanceof TextMessage) {
                        terminalBuffer.append(((TextMessage) msg).getText());
                        onMessage(((TextMessage) msg).getText());
                    }
                }).async();
        try {
            connection.sendMessage(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 远程调用获取远程系统信息
     * @return 系统信息
     */
    @Override
    public Map<String, Object> getSystemInfo() {
        CommandMessage cmd = new CommandMessage();
        cmd.setCommand(CommandType.SYS_INFO);
        cmd.addAddition(CommandKey.PER_ID,remoteClientId);
        final CountDownLatch latch = new CountDownLatch(1);
        new ConnectionPromise(cmd)
                .success((conn,msg)->{
                    if (msg instanceof TextMessage){

                        systemInfo = JsonUtils.fromJson(((TextMessage) msg).getText(),new TypeToken<Map<String,Object>>(){}.getType());
                    }
                    latch.countDown();
                }).async();
        try {
            connection.sendMessage(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return systemInfo;
    }
}
