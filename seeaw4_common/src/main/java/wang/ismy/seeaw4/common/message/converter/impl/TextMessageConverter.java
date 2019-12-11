package wang.ismy.seeaw4.common.message.converter.impl;

import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.message.MessageType;
import wang.ismy.seeaw4.common.message.converter.MessageConverter;
import wang.ismy.seeaw4.common.message.impl.TextMessage;

import java.util.Map;

/**
 * @author my
 */
public class TextMessageConverter implements MessageConverter {
    @Override
    public Message convert(byte[] payload, Map<String, Object> additions) {
        TextMessage textMessage = new TextMessage(new String(payload),additions);
        return textMessage;
    }

    @Override
    public MessageType messageType() {
        return MessageType.TEXT;
    }
}
