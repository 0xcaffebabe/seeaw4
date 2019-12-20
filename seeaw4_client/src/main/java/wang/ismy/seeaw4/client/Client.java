package wang.ismy.seeaw4.client;

import lombok.extern.slf4j.Slf4j;
import wang.ismy.seeaw4.client.message.chain.ClientCommandMessageChain;
import wang.ismy.seeaw4.client.netty.NettyClientConnection;
import wang.ismy.seeaw4.client.terminal.TerminalProxy;
import wang.ismy.seeaw4.common.client.Per;
import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.connection.ConnectionListener;
import wang.ismy.seeaw4.common.message.MessageService;
import wang.ismy.seeaw4.common.message.chain.impl.PrintMessageChain;
import wang.ismy.seeaw4.common.message.chain.impl.PromiseMessageChain;
import wang.ismy.seeaw4.common.utils.SwingUtils;
import wang.ismy.seeaw4.terminal.Resolution;
import wang.ismy.seeaw4.terminal.Terminal;
import wang.ismy.seeaw4.terminal.camera.Camera;
import wang.ismy.seeaw4.terminal.desktop.Desktop;
import wang.ismy.seeaw4.terminal.enums.ImgType;
import wang.ismy.seeaw4.terminal.impl.CommonTerminal;
import wang.ismy.seeaw4.terminal.observer.impl.LazyTerminalObserver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * 客户端
 * @author MY
 */
@Slf4j
public class Client {

    private ClientService clientService;
    private Connection connection;
    private ConnectionListener connectionListener;
    private Terminal terminal;
    private TerminalProxy terminalProxy;
    private List<Per> clientList = new ArrayList<>();

    public Client() {
        // 创建一个本地终端
        try {
            terminal = new CommonTerminal();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 创建一个远程终端代理
        terminalProxy = new TerminalProxy();
    }

    public void setConnectionListener(ConnectionListener listener) {
        connectionListener = listener;
    }

    public void init() {
        // 注册消息处理链
        MessageService.getInstance().registerMessageChain(new PrintMessageChain()
                , PromiseMessageChain.getInstance(),new ClientCommandMessageChain(terminal,terminalProxy,this));
        // 连接服务端
        NettyClientConnection connection = new NettyClientConnection("127.0.0.1", 1999);
        connection.bindConnectionListener(connectionListener);
        // 向连接设置客户自定义的连接监听器
        connection.connect();
        this.connection = connection;
        clientService = new ClientService(connection);
        terminalProxy.setConnection(connection);
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.init();
        client.terminalProxy.registerObserver(new LazyTerminalObserver() {
            @Override
            public void onMessage(String msg) {
                System.err.print(msg);
            }
        });
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String str = scanner.nextLine();
            if (str.contains("-")){
                client.terminalProxy.setRemoteClientId(str);
                client.terminalProxy.bind();
            }else if(str.equals("camera")){
                Camera camera = client.terminalProxy.getCamera();
                byte[] bytes = camera.getCameraSnapshot(ImgType.JPEG, new Resolution(640, 480));
                SwingUtils.showImg(bytes);

            }else if(str.equals("screen")){
                Desktop desktop = client.terminalProxy.getDesktop();
                byte[] screen = desktop.getScreen(ImgType.JPEG, new Resolution(1366, 768));
                SwingUtils.showImg(screen);
            }else if(str.equals("buffer")){
                String buffer = client.terminalProxy.getTerminalBuffer();
                System.out.print(buffer);
            }
            else{
                client.terminalProxy.input(str);
            }
        }
        System.in.read();
    }


    public Terminal getTerminal() {
        return terminal;
    }

    public void onClientListChange(List<Per> clientList){
        System.out.println("客户端列表发生变化:"+clientList);
        // 更新selfid
        for (Per per : clientList) {
            if (per.isSelf()){
                terminalProxy.setSelfId(per.getId());
                break;
            }
        }
        this.clientList = clientList;
    }
}
