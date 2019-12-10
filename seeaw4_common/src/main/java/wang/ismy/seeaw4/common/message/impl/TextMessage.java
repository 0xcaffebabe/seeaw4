package wang.ismy.seeaw4.common.message.impl;

import wang.ismy.seeaw4.common.message.Message;

import java.nio.charset.Charset;

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
}
