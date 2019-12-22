package wang.ismy.seeaw4.client.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;
import wang.ismy.seeaw4.common.connection.ConnectionListener;
import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.connection.ConnectionInfo;
import wang.ismy.seeaw4.common.message.MessageListener;
import wang.ismy.seeaw4.common.message.MessageService;
import wang.ismy.seeaw4.common.message.SelfMessageEncoder;
import wang.ismy.seeaw4.common.message.chain.impl.PrintMessageChain;
import wang.ismy.seeaw4.common.message.handler.IdleChannelHandler;
import wang.ismy.seeaw4.common.message.impl.TextMessage;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * netty客户端连接
 *
 * @author my
 */
@Slf4j
public class NettyClientConnection implements Connection {

    private static final long NEXT_RETRY_DELAY = 5000;
    private Channel channel;
    private ConnectionInfo connectionInfo;
    private MessageListener messageListener;
    private String ip;
    private int port;
    private NettyClientHandler nettyClientHandler;
    private final MessageService messageService = MessageService.getInstance();
    private NioEventLoopGroup group;
    private ConnectionListener connectionListener;

    public NettyClientConnection(String ip, int port) {
        nettyClientHandler = new NettyClientHandler(this);
        this.ip = ip;
        this.port = port;
    }

    public void connect() {
        group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ByteBuf delimiter = Unpooled.copiedBuffer("$_0xca".getBytes());

                        ch.pipeline()
                                .addLast("encoder",new SelfMessageEncoder())
                                .addLast("decoder",new DelimiterBasedFrameDecoder(1024*1024,delimiter))
                                .addLast(nettyClientHandler);
                    }
                });
        final ChannelFuture future = bootstrap.connect(ip, port);
        this.channel = future.channel();
        // 如果连接不上自动重连
        future.addListener((ChannelFuture f) -> {
            if (!f.isSuccess()) {
                f.channel().eventLoop().schedule(() -> {
                    log.info("连接不上服务器,{}ms后重试", NEXT_RETRY_DELAY);
                    connect();
                }, NEXT_RETRY_DELAY, TimeUnit.MILLISECONDS);
            }else {
                connectionInfo = new ConnectionInfo(channel.remoteAddress(),System.currentTimeMillis());
            }
        });
    }

    @Override
    public void close() throws IOException {
        group.shutdownGracefully();
        nettyClientHandler.setInitClose(true);
        channel.close();
    }

    @Override
    public ConnectionInfo getInfo() {
        return connectionInfo;
    }

    @Override
    public void sendMessage(Message message) throws IOException {
        byte[] build = messageService.build(message);
        channel.writeAndFlush(
                Unpooled.wrappedBuffer(build));
    }

    @Override
    public void bindMessageListener(MessageListener listener) {
        messageListener = listener;
    }

    public void onMessage(ByteBuf buf) {
        Message message = messageService.resolve(buf.readBytes(buf.readableBytes()).array());
        messageService.process(this, message);
        // 通知监听者
        if (messageListener != null){
            messageListener.onMessage(this,message);
        }
    }

    @Override
    public void active() {
        if (connectionListener != null){
            connectionListener.establish(this);
        }
    }

    @Override
    public void inActive() {
        if (connectionListener != null){
            connectionListener.close(this);
        }
    }

    @Override
    public void bindConnectionListener(ConnectionListener listener) {
        this.connectionListener = listener;
    }

    @Override
    public String toString() {
        return "NettyClientConnection{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }
}
