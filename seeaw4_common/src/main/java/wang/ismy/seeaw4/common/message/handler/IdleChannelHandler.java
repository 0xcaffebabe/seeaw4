package wang.ismy.seeaw4.common.message.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author MY
 * @date 2019/12/22 15:59
 */
@ChannelHandler.Sharable
@Slf4j
public class IdleChannelHandler extends IdleStateHandler {

    public IdleChannelHandler(long readerIdleTime, long writerIdleTime, long allIdleTime, TimeUnit unit) {
        super(readerIdleTime, writerIdleTime, allIdleTime, unit);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        switch (evt.state()){
            case READER_IDLE:
                log.info("读空闲");
                break;
            case WRITER_IDLE:
                log.info("写空闲");
                break;
            case ALL_IDLE:
                log.info("读写空闲");
                break;
            default:
        }

    }
}
