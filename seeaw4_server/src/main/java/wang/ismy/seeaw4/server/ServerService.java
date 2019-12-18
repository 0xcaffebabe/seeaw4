package wang.ismy.seeaw4.server;

import wang.ismy.seeaw4.common.client.Per;
import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.promise.ConnectionPromise;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 包装一些比较繁琐的操作
 * @author my
 */
public class ServerService {

    private Server server;

    public ServerService(Server server) {
        this.server = server;
    }

    /**
     * 发送一条带有回调的消息
     * @param connection 发送的对方
     * @param message　发送的消息
     */
    public void sendCallbackMessage(Connection connection, Message message, ConnectionPromise.SuccessCallback callback) throws IOException {
        new ConnectionPromise(message)
                .success(callback)
                .async();
        connection.sendMessage(message);
    }

    public List<Per> getPerList(){
        return server.getConnector().getConnectionList()
                .stream()
                .map(Per::convert)
                .collect(Collectors.toList());
    }

}
