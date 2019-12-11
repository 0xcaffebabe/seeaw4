package wang.ismy.seeaw4.common.message.impl;

import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.message.MessageType;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * 文本消息
 * @author my
 */
public class TextMessage implements Message {

    private byte[] payload;

    public TextMessage(String msg) {
        payload = msg.getBytes();
    }

    @Override
    public byte[] getPayload() {
        return payload;
    }

    @Override
    public MessageType messageType() {
        return MessageType.TEXT;
    }

    @Override
    public Map<String, Object> addition() {
        return null;
    }
}
