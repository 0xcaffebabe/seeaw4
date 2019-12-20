package wang.ismy.seeaw4.client.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * @author my
 */
@Slf4j
@ChannelHandler.Sharable
public class NettyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private NettyClientConnection connection;

    /**
     * 是否为主动关闭
     */
    private boolean isInitClose = false;

    public NettyClientHandler(NettyClientConnection connection) {
        this.connection = connection;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("连接服务器成功");
        // 通知连接建立
        connection.active();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 通知连接已关闭
        connection.inActive();
        if (!isInitClose){
            log.info("客户端与服务器连接断开,正重新连接");
            if (connection != null){
                connection.connect();
            }
        }else {
            log.info("连接主动关闭");
        }

    }

    public void setNettyConnection(NettyClientConnection connection){
        this.connection = connection;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        log.info("客户端接收到消息");
        if (connection != null){
            connection.onMessage(msg);
        }
    }

    public void setInitClose(boolean initClose) {
        isInitClose = initClose;
    }
}
