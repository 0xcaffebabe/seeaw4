package wang.ismy.seeaw4.common.message.converter.impl;

import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.message.MessageType;
import wang.ismy.seeaw4.common.message.converter.MessageConverter;

import java.util.Map;

/**
 * 图片消息转换器
 * @author my
 */
public class ImgMessageConverter implements MessageConverter {
    @Override
    public Message convert(byte[] payload, Map<String, Object> additions) {
        return null;
    }

    @Override
    public MessageType messageType() {
        return MessageType.IMG;
    }
}
