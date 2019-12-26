package wang.ismy.seeaw4.server.netty;

import io.netty.channel.Channel;
import io.netty.handler.stream.ChunkedNioFile;
import wang.ismy.seeaw4.common.ExecuteService;
import wang.ismy.seeaw4.common.client.Per;
import wang.ismy.seeaw4.common.command.CommandType;
import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.message.impl.CommandMessage;
import wang.ismy.seeaw4.common.message.impl.HeartBeatMessage;
import wang.ismy.seeaw4.common.utils.JsonUtils;
import wang.ismy.seeaw4.server.service.AuthService;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 负责维护netty连接与逻辑连接之间的双向映射关系
 * @author my
 */
public class NettyConnectionService {
    private static final NettyConnectionService INSTANCE = new NettyConnectionService();
    private Map<Channel,Connection> channelConnectionMap = new ConcurrentHashMap<>();
    private Map<Connection,Channel> connectionChannelMap = new ConcurrentHashMap<>();
    private AuthService authService = AuthService.getInstance();

    private NettyConnectionService() {
        // 每隔10秒，向所有连接发送一条心跳
//        ExecuteService.schedule(()->{
//            for (Connection connection : getConnectionList()) {
//                try {
//                    connection.sendMessage(new HeartBeatMessage());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        },10);
    }

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
        broadcast();
        return connection;
    }

    public Channel remove(Connection connection){
        Channel channel = connectionChannelMap.get(connection);
        connectionChannelMap.remove(connection);
        channelConnectionMap.remove(channel);
        broadcast();
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

    /**
     * 全体广播
     */
    public void broadcast(){
        ExecuteService.excutes(()->{
            for (Connection conn : getConnectionList()) {
                Channel channel = get(conn);
                // 不向还未认证的客户发送消息
                if (!authService.contains(channel)){
                    continue;
                }
                List<Per> collect = getConnectionList().stream()
                        .map(c -> {
                            Per p = Per.convert(c);
                            if (c.equals(conn)) {
                                p.setSelf(true);
                            }
                            return p;
                        }).collect(Collectors.toList());
                CommandMessage cmd = new CommandMessage();
                cmd.setPayload(JsonUtils.toJson(collect).getBytes());
                cmd.setCommand(CommandType.LIST_CLIENT);
                try {
                    conn.sendMessage(cmd);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 向单个客户发送在线客户列表
     * @param channel
     */
    public void broadcast(Channel channel){
        ExecuteService.excutes(()->{

            Connection conn = get(channel);
            if (conn != null){
                // 不向还未认证的客户发送消息
                if (!authService.contains(channel)){
                    return;
                }
                List<Per> collect = getConnectionList().stream()
                        .map(c -> {
                            Per p = Per.convert(c);
                            if (c.equals(conn)) {
                                p.setSelf(true);
                            }
                            return p;
                        }).collect(Collectors.toList());
                CommandMessage cmd = new CommandMessage();
                cmd.setPayload(JsonUtils.toJson(collect).getBytes());
                cmd.setCommand(CommandType.LIST_CLIENT);
                try {
                    conn.sendMessage(cmd);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
