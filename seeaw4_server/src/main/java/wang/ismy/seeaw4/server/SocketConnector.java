package wang.ismy.seeaw4.server;

import wang.ismy.seeaw4.common.Connector;
import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.connection.OnConnectionCloseListener;
import wang.ismy.seeaw4.common.connection.OnConnectionEstablishListener;

import javax.net.ServerSocketFactory;
import java.util.List;

/**
 * socket连接器
 * @author my
 */
public class SocketConnector implements Connector {

    private OnConnectionEstablishListener establishListener;
    private OnConnectionCloseListener closeListener;

    private ServerSocketService serverSocketService = new ServerSocketService();

    public SocketConnector() {
        serverSocketService.init();
    }

    @Override
    public void bindConnectListener(OnConnectionEstablishListener listener) {
        establishListener = listener;
    }

    @Override
    public void bindDisconnectListener(OnConnectionCloseListener listener) {
        closeListener = listener;

    }

    @Override
    public List<Connection> getConnectionList() {
        return null;
    }
}
