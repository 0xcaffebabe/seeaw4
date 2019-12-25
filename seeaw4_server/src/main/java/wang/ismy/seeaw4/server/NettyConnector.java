package wang.ismy.seeaw4.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import wang.ismy.seeaw4.common.Connector;
import wang.ismy.seeaw4.common.ExecuteService;
import wang.ismy.seeaw4.common.client.Per;
import wang.ismy.seeaw4.common.command.CommandType;
import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.connection.ConnectionListener;
import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.message.MessageListener;
import wang.ismy.seeaw4.common.message.MessageService;
import wang.ismy.seeaw4.common.message.SelfMessageEncoder;
import wang.ismy.seeaw4.common.message.chain.impl.PrintMessageChain;
import wang.ismy.seeaw4.common.message.chain.impl.PromiseMessageChain;
import wang.ismy.seeaw4.common.message.impl.CommandMessage;
import wang.ismy.seeaw4.common.utils.JsonUtils;
import wang.ismy.seeaw4.server.message.chain.ServerMessageChain;
import wang.ismy.seeaw4.server.netty.*;
import wang.ismy.seeaw4.server.service.AuthService;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * socket连接器
 * @author my
 */
@Slf4j
public class NettyConnector implements Connector, ChannelListener, MessageListener {

    private ConnectionListener connectionListener;
    private ExecuteService executeService = ExecuteService.getInstance();
    private NettyServerHandler nettyServerHandler = NettyServerHandler.getInstance();
    private NettyConnectionService nettyConnectionService = NettyConnectionService.getInstance();
    private MessageService messageService = MessageService.getInstance();
    private AuthService authService = AuthService.getInstance();

    public NettyConnector() {
        // 监听handler的连接建立与关闭
        nettyServerHandler.setChannelListener(this);
        // 监听消息
        nettyServerHandler.setMessageListener(this);
        // 注册消息处理链
        messageService.registerMessageChain(new PrintMessageChain(),PromiseMessageChain.getInstance());
        messageService.registerMessageChain(new ServerMessageChain());
        // 启动netty服务器
        executeService.excute(()->{
            NioEventLoopGroup mainGroup = new NioEventLoopGroup();
            NioEventLoopGroup subGroup = new NioEventLoopGroup();
            try{
                // 启动对象
                ServerBootstrap serverBootstrap = new ServerBootstrap();

                serverBootstrap
                        .group(mainGroup,subGroup)
                        // 通道类型
                        .channel(NioServerSocketChannel.class)

                        // 业务处理
                        .childHandler(new ChannelInitializer<Channel>() {
                            @Override
                            protected void initChannel(Channel channel) throws Exception {
                                ByteBuf delimiter = Unpooled.copiedBuffer("$_0xca".getBytes());
                                channel.pipeline()
                                        .addLast("encoder",new SelfMessageEncoder())
                                        .addLast("decoder",new DelimiterBasedFrameDecoder(1024*1024,delimiter))
                                        .addLast(new IdleStateHandler(5,10,15, TimeUnit.SECONDS))
                                        .addLast(nettyServerHandler);
                            }
                        });
                ChannelFuture sync = serverBootstrap.bind(1999).sync();
                log.info("netty服务器启动成功");
                // 等待关闭
                sync.channel().closeFuture().sync();
            }catch (Exception e){
                log.error("netty服务器发生错误,{}",e.getMessage());
                e.printStackTrace();
            }finally {
                mainGroup.shutdownGracefully();
                subGroup.shutdownGracefully();
            }
        });
    }


    @Override
    public void bindConnectionListener(ConnectionListener connectionListener) {
        this.connectionListener = connectionListener;
    }

    @Override
    public List<Connection> getConnectionList() {
        return nettyConnectionService.getConnectionList();
    }

    @Override
    public void channelActive(Channel channel) throws Exception {
        Connection connection = new NettyConnection(channel);
        nettyConnectionService.add(channel,connection);
        // 通知监听器
        if (connectionListener != null){
            connectionListener.establish(connection);
        }
        broadcast();
    }

    @Override
    public void channelInactive(Channel channel) throws Exception {
        Connection connection = nettyConnectionService.remove(channel);
        // 通知监听器
        if (connectionListener != null){
            connectionListener.close(connection);
        }
        broadcast();
    }

    @Override
    public void onMessage(Connection connection,Message message) {
        messageService.process(connection,message);
    }

    private void broadcast() {

        executeService.excute(()->{

            for (Connection conn : getConnectionList()) {
                Channel channel = nettyConnectionService.get(conn);
                // 不向还未认证的客户发送消息
                if (!authService.contains(channel)){
                    continue;
                }
                List<Per> collect = nettyConnectionService.getConnectionList().stream()
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
