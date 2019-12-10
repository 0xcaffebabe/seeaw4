package wang.ismy.seeaw4.client.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

/**
 * @author my
 */
@ChannelHandler.Sharable
@Slf4j
public class NettyClientHandler extends SimpleChannelInboundHandler<ByteBuffer> {

    private static NettyClientHandler INSTANCE = new NettyClientHandler();
    private NettyClientConnection connection;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("连接服务器成功");

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端与服务器连接断开,正重新连接");
        if (connection != null){
            connection.connect();
        }
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, ByteBuffer msg) throws Exception {

    }

    public static NettyClientHandler getInstance(){
        return INSTANCE;
    }

    public void setNettyConnection(NettyClientConnection connection){
        this.connection = connection;
    }
}
