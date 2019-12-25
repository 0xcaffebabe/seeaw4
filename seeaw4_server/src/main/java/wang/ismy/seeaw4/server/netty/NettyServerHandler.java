package wang.ismy.seeaw4.server.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.message.MessageListener;
import wang.ismy.seeaw4.common.message.MessageService;
import wang.ismy.seeaw4.common.message.chain.impl.PrintMessageChain;
import wang.ismy.seeaw4.common.message.impl.AuthMessage;
import wang.ismy.seeaw4.common.promise.ConnectionPromise;
import wang.ismy.seeaw4.server.service.AuthService;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 提供netty与nettyConnector的一个接口
 *
 * @author my
 */
@Slf4j
@ChannelHandler.Sharable
public class NettyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private static final NettyServerHandler INSTANCE = new NettyServerHandler();
    private List<Channel> channelList = new LinkedList<>();
    private ChannelListener channelListener;
    private MessageService messageService = MessageService.getInstance();
    private NettyConnectionService connectionService = NettyConnectionService.getInstance();
    private MessageListener messageListener;
    private AuthService authService = AuthService.getInstance();
    private String password = System.getProperty("seeaw4.password") != null ? System.getProperty("seeaw4.password") : "password";

    private NettyServerHandler() {
    }

    /**
     * 连接建立时调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        log.info("连接到达:{}", channel.remoteAddress());
        channelList.add(channel);
        // 通知监听者
        if (channelListener != null) {
            channelListener.channelActive(channel);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Connection connection = connectionService.get(ctx.channel());
        log.info("{}发生异常:{}", connection, cause.getMessage());
    }

    /**
     * 连接关闭时调用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelList.remove(channel);
        log.info("连接关闭:{}", channel.remoteAddress());
        // 通知监听者
        if (channelListener != null) {
            channelListener.channelInactive(channel);
        }
    }

    /**
     * 数据到达时调用
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        // 进行认证，认证不通过将抛出异常
        byte[] array = msg.readBytes(msg.readableBytes()).array();
        Message message = messageService.resolve(array);
        Channel channel = ctx.channel();
        if (!auth(channel, message)) {
            log.info("{}未认证，禁止通过", channel);
            throw new RuntimeException("channel unauthorized");
        }

        // 通知监听者
        if (messageListener != null) {
            messageListener.onMessage(connectionService.get(channel), message);
        }
    }

    public static NettyServerHandler getInstance() {
        return INSTANCE;
    }

    public List<Channel> getChannelList() {
        return channelList;
    }

    public void setChannelListener(ChannelListener channelListener) {
        this.channelListener = channelListener;
    }

    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    private boolean auth(Channel channel, Message msg) throws IOException {
        // 判断消息类型是否是认证消息
        if (msg instanceof AuthMessage) {
            // 如果密码正确，则将其加入已认证连接列表
            if (password.equals(((AuthMessage) msg).getPassword())) {
                authService.add(channel);
                // 认证成功后，向该连接发送在线客户端列表
                log.info("{}认证成功，发送在线客户列表", channel);
                connectionService.broadcast(channel);
                return true;
            }
        }
        // 如果认证列表没有这个连接，丢弃其发送的消息
        return authService.contains(channel);
    }


}
