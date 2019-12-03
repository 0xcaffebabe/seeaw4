package wang.ismy.seeaw4.server;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

/**
 * @author my
 */
@Slf4j
public class ServerSocketService {

    private ServerSocket serverSocket;
    private ExecuteService executeService = ExecuteService.getInstance();
    private boolean init=false;

    public void init() {
        try {
            serverSocket = new ServerSocket(1999);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("建立serverSocket失败:{}",e.getMessage());
            System.exit(-1);
        }
        init=true;
    }

    public Socket accept() throws IOException {
        if (!init){
            throw new IllegalStateException("ServerSocketService 还没初始化!");
        }
        return serverSocket.accept();
    }

    public void socketAccept(Consumer<Socket> consumer){

    }
}
