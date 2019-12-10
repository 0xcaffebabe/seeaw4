package wang.ismy.seeaw4.server.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author my
 */
public interface ChannelListener {

    /**
     * 通道建立时调用
     * @param channel　通道
     * @throws Exception
     */
    void channelActive(Channel channel) throws Exception ;

    /**
     * 通道关闭时调用
     * @param ctx
     * @throws Exception
     */
    void channelInactive(Channel ctx) throws Exception ;
}
