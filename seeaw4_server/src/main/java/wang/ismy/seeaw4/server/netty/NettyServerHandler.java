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

import java.lang.annotation.ElementType;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 提供netty与nettyConnector的一个接口
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

    private NettyServerHandler() { }

    /**
     * 连接建立时调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        Channel channel = ctx.channel();
        log.info("连接到达:{}",channel.remoteAddress());
        channelList.add(channel);
        // 通知监听者
        if (channelListener != null){
            channelListener.channelActive(channel);
        }
    }

    /**
     * 连接关闭时调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelList.remove(channel);
        log.info("连接关闭:{}",channel.remoteAddress());
        // 通知监听者
        if (channelListener != null){
            channelListener.channelInactive(channel);
        }
    }

    /**
     * 数据到达时调用
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        Channel channel = ctx.channel();
        Message message = messageService.resolve(msg.array());
        // 通知监听者
        if (messageListener != null){
            messageListener.onMessage(connectionService.get(channel),message);
        }
    }

    public static NettyServerHandler getInstance(){
        return INSTANCE;
    }

    public List<Channel> getChannelList(){
        return channelList;
    }

    public void setChannelListener(ChannelListener channelListener) {
        this.channelListener = channelListener;
    }

    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }
}
