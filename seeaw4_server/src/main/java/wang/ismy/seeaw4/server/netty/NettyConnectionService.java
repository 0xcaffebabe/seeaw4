package wang.ismy.seeaw4.server.netty;

import io.netty.channel.Channel;
import io.netty.handler.stream.ChunkedNioFile;
import wang.ismy.seeaw4.common.connection.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 负责维护netty连接与逻辑连接之间的双向映射关系
 * @author my
 */
public class NettyConnectionService {
    private static final NettyConnectionService INSTANCE = new NettyConnectionService();
    private Map<Channel,Connection> channelConnectionMap = new ConcurrentHashMap<>();
    private Map<Connection,Channel> connectionChannelMap = new ConcurrentHashMap<>();

    private NettyConnectionService() { }

    public void add(Channel channel, Connection connection){
        channelConnectionMap.put(channel,connection);
        connectionChannelMap.put(connection, channel);
    }

    public void add(Connection connection,Channel channel){
        add(channel,connection);
    }

    public Connection get(Channel channel){
        return channelConnectionMap.get(channel);
    }

    public Channel get(Connection connection){
        return connectionChannelMap.get(connection);
    }

    public Connection remove(Channel channel){
        Connection connection = channelConnectionMap.get(channel);
        channelConnectionMap.remove(channel);
        connectionChannelMap.remove(connection);
        return connection;
    }

    public Channel remove(Connection connection){
        Channel channel = connectionChannelMap.get(connection);
        connectionChannelMap.remove(connection);
        channelConnectionMap.remove(channel);
        return channel;
    }

    public List<Connection> getConnectionList(){
        return new ArrayList<>(connectionChannelMap.keySet());
    }

    public List<Channel> getChannelList(){
        return new ArrayList<>(channelConnectionMap.keySet());
    }

    public Connection getConnectionById(String id){
        for (Connection connection : getConnectionList()) {
            if (connection.getInfo().getConnectionId().equals(id)){
                return connection;
            }
        }
        return null;
    }
    public static NettyConnectionService getInstance(){
        return INSTANCE;
    }


}
