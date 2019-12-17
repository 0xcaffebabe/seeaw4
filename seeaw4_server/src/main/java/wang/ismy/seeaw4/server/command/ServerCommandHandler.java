package wang.ismy.seeaw4.server.command;

import wang.ismy.seeaw4.common.client.Client;
import wang.ismy.seeaw4.common.command.CommandHandler;
import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.message.impl.CommandMessage;
import wang.ismy.seeaw4.common.message.impl.TextMessage;
import wang.ismy.seeaw4.common.utils.JsonUtils;
import wang.ismy.seeaw4.server.netty.NettyConnectionService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MY
 * @date 2019/12/17 10:12
 */
public class ServerCommandHandler implements CommandHandler {

    private final NettyConnectionService connectionService = NettyConnectionService.getInstance();

    @Override
    public Message process(Connection connection, CommandMessage commandMessage) {

        switch (commandMessage.getCommand()) {
            case LIST_CLIENT:
                return getClientList(connection, commandMessage);
            default:
                return null;
        }
    }

    private Message getClientList(Connection connection, CommandMessage message) {
        List<Connection> connectionList = connectionService.getConnectionList();
        List<Client> ret = new ArrayList<>();
        for (Connection conn : connectionList) {
            Client client = new Client();
            client.setIp(conn.getInfo().getSocketAddress().getAddress().getHostAddress());
            client.setPort(conn.getInfo().getSocketAddress().getPort());
            client.setConnectTime(conn.getInfo().getConnectedTime());
            ret.add(client);
        }

        return new TextMessage(JsonUtils.toJson(ret));
    }
}
