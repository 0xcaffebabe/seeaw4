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
public class TextMessage extends Message {

    public TextMessage(byte[] payload, Map<String, Object> addition) {
        super(payload, addition);
    }

    public TextMessage(String text){
        super();
        payload = text.getBytes();
    }

    public void setText(String msg) {
        payload = msg.getBytes();
    }

    public String getText(){
        return new String(payload);
    }

    @Override
    public MessageType messageType() {
        return MessageType.TEXT;
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
                addition.equals(that.addition);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(addition);
        result = 31 * result + Arrays.hashCode(payload);
        return result;
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "payload=" + new String(payload) +
                ", additions=" + addition +
                '}';
    }
}
