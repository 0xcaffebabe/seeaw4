package wang.ismy.seeaw4.client;

import wang.ismy.seeaw4.common.command.CommandType;
import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.message.impl.CommandMessage;
import wang.ismy.seeaw4.common.promise.ConnectionPromise;

import java.io.IOException;
import java.util.List;

/**
 * @author MY
 * @date 2019/12/17 15:32
 */
public class ClientService {

    private Connection connection;

    public ClientService(Connection connection){
        this.connection = connection;
    }

    /**
     * 获取服务端在线的客户列表
     * @param callback 数据获取成功回调函数
     * @throws IOException
     */
    public void selectClientList(ConnectionPromise.SuccessCallback callback) throws IOException {
        CommandMessage message = new CommandMessage();
        message.setCommand(CommandType.LIST_CLIENT);
        new ConnectionPromise(message)
                .success(callback)
                .async();
        connection.sendMessage(message);
    }

    public void sendCallbackMessage(Message message, ConnectionPromise.SuccessCallback callback){
        new ConnectionPromise(message)
                .success(callback)
                .async();
        try {
            connection.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
