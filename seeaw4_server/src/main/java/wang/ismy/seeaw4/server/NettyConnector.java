package wang.ismy.seeaw4.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import wang.ismy.seeaw4.common.Connector;
import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.connection.OnConnectionCloseListener;
import wang.ismy.seeaw4.common.connection.OnConnectionEstablishListener;
import wang.ismy.seeaw4.server.netty.NettyServerHandler;

import java.util.List;

/**
 * socket连接器
 * @author my
 */
@Slf4j
public class NettyConnector implements Connector {

    private OnConnectionEstablishListener establishListener;
    private OnConnectionCloseListener closeListener;
    private ExecuteService executeService = ExecuteService.getInstance();
    private NettyServerHandler handler = NettyServerHandler.getInstance();

    public NettyConnector() {
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
                        .childHandler(new ChannelInitializer<>() {
                            @Override
                            protected void initChannel(Channel channel) throws Exception {
                                channel.pipeline().addLast(handler);
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
