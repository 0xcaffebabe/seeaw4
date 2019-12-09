package wang.ismy.seeaw4.server.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author my
 */
@Slf4j
public class NettyWebServerHandler extends ChannelHandlerAdapter {

    private List<Channel> channelList = new LinkedList<>();

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
    }

    /**
     * 数据到达时调用
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }
}
