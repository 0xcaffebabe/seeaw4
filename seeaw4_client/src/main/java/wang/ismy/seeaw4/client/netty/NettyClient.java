package wang.ismy.seeaw4.client.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import wang.ismy.seeaw4.common.ExecuteService;

/**
 * 对netty的操作进行封装
 * @author my
 */
public class NettyClient {

    private ExecuteService executeService = ExecuteService.getInstance();

    public NettyClient() {
        executeService.excute(()->{
            NioEventLoopGroup group = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //ch.pipeline().addLast(new ClientHandler());
                        }
                    });
            ChannelFuture future = null;
            try {
                future = bootstrap.connect("127.0.0.1", 1999).sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Channel channel = future.channel();
        });
    }
}
