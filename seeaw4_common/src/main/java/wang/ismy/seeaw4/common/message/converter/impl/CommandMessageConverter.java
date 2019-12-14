package wang.ismy.seeaw4.common.message.converter.impl;

import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.message.MessageType;
import wang.ismy.seeaw4.common.message.converter.MessageConverter;
import wang.ismy.seeaw4.common.message.impl.CommandMessage;

import java.util.Map;

/**
 * @author MY
 * @date 2019/12/14 17:47
 */
public class CommandMessageConverter implements MessageConverter {
    @Override
    public Message convert(byte[] payload, Map<String, Object> additions) {
        return new CommandMessage(payload,additions);
    }

    @Override
    public MessageType messageType() {
        return MessageType.COMMAND;
    }
}
