package wang.ismy.seeaw4.common.message.impl;

import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.message.MessageType;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * 文本消息
 *
 * @author my
 */
public class TextMessage implements Message {

    private byte[] payload;
    private Map<String, Object> additions;

    public TextMessage(String msg, Map<String, Object> additions) {
        payload = msg.getBytes();
        this.additions = additions;
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
        return additions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TextMessage)) {
            return false;
        }
        TextMessage that = (TextMessage) o;
        return Arrays.equals(payload, that.payload) &&
                additions.equals(that.additions);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(additions);
        result = 31 * result + Arrays.hashCode(payload);
        return result;
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "payload=" + new String(payload) +
                ", additions=" + additions +
                '}';
    }
}
