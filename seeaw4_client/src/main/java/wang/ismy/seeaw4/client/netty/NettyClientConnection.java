package wang.ismy.seeaw4.client.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import wang.ismy.seeaw4.common.Message;
import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.connection.ConnectionInfo;
import wang.ismy.seeaw4.common.listener.MessageListener;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * netty客户端连接
 * @author my
 */
@Slf4j
public class NettyClientConnection implements Connection {

    private static final long NEXT_RETRY_DELAY = 5000;
    private Channel channel;
    private ConnectionInfo connectionInfo;
    private MessageListener messageListener;

    public NettyClientConnection() {
        connect();
    }

    private void connect() {
        NioEventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {

                    }
                });
        final ChannelFuture future = bootstrap.connect("127.0.0.1", 1999);
        // 如果连接不上自动重连
        future.addListener((ChannelFuture f)->{
            if (!f.isSuccess()) {

                f.channel().eventLoop().schedule(()->{
                    log.info("连接不上服务器,{}ms后重试",NEXT_RETRY_DELAY);
                    connect();
                },NEXT_RETRY_DELAY, TimeUnit.MILLISECONDS); // or you can give up at some point by just doing nothing.
            }else {
                log.info("连接服务器成功");
                channel = future.channel();
                connectionInfo = new ConnectionInfo(channel.remoteAddress(),System.currentTimeMillis());
            }
        });

    }

    @Override
    public void close() throws IOException {
        channel.close();
    }

    @Override
    public ConnectionInfo getInfo() {
        return connectionInfo;
    }

    @Override
    public void sendMessage(Message message) throws IOException {

    }

    @Override
    public void bindMessageListener(MessageListener listener) {
        messageListener = listener;
    }
}
