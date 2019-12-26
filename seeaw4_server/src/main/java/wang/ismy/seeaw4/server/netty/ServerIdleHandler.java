package wang.ismy.seeaw4.server.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 服务端idle处理器
 * @author my
 */
@ChannelHandler.Sharable
@Slf4j
public class ServerIdleHandler extends IdleStateHandler {

    private NettyConnectionService connectionService = NettyConnectionService.getInstance();
    private NettyServerHandler handler = NettyServerHandler.getInstance();

    public ServerIdleHandler(long readerIdleTime, long writerIdleTime, long allIdleTime, TimeUnit unit) {
        super(readerIdleTime, writerIdleTime, allIdleTime, unit);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        log.info("channel idle event{}",evt.state());
        if (evt.state().equals(IdleState.ALL_IDLE)){
            log.info("{}读写超时，剔除",ctx.channel());
            connectionService.remove(ctx.channel());
            ctx.channel().close();
        }

    }
}
