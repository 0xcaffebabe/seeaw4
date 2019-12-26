package wang.ismy.seeaw4.common.message.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import wang.ismy.seeaw4.common.connection.ConnectionListener;
import wang.ismy.seeaw4.common.connection.ConnectionState;
import wang.ismy.seeaw4.common.connection.ConnectionStateChangeListener;

import java.util.concurrent.TimeUnit;

/**
 * @author MY
 * @date 2019/12/22 15:59
 */
@ChannelHandler.Sharable
@Slf4j
public class IdleChannelHandler extends IdleStateHandler {

    private ConnectionStateChangeListener listener;

    public IdleChannelHandler(long readerIdleTime, long writerIdleTime, long allIdleTime, TimeUnit unit) {
        super(readerIdleTime, writerIdleTime, allIdleTime, unit);
    }

    public void setConnectionStateChangeListener(ConnectionStateChangeListener listener){
        this.listener = listener;
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        // 触发这个事件，代表连接可能已经断开了
        if (evt.state() == IdleState.ALL_IDLE) {
            log.info("{}读写空闲", ctx.channel());
            if (listener != null) {
                listener.onChange(null, ConnectionState.DEAD);
            }
        }
    }
}
