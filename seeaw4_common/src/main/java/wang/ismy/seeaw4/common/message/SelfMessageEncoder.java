package wang.ismy.seeaw4.common.message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import wang.ismy.seeaw4.common.utils.BytesUtils;

/**
 * @author MY
 * @date 2019/12/17 18:22
 */
@Slf4j
public class SelfMessageEncoder extends MessageToByteEncoder<ByteBuf> {

    private static final byte[] delimiter = "$_0xca".getBytes();
    private MessageService messageService = MessageService.getInstance();
    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
        byte[] bytes = msg.readBytes(msg.readableBytes()).array();
        Message resolve = messageService.resolve(bytes);
        log.info("encode 数据,{}",resolve);
        out.writeBytes(BytesUtils.concat(bytes,delimiter));
    }
}
