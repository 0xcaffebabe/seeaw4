package wang.ismy.seeaw4.common.message;

import wang.ismy.seeaw4.common.message.converter.MessageConverter;
import wang.ismy.seeaw4.common.message.converter.MessageConverterFactory;
import wang.ismy.seeaw4.common.message.converter.impl.TextMessageConverter;

/**
 * 消息类型
 * @author my
 */

public enum MessageType {

    /**
     * 文本消息
     */
    TEXT(0,MessageConverterFactory.textMessageConverter()),

    /**
     * 图片消息
     */
    IMG(1,MessageConverterFactory.imgMessageConverter());

    private int code;
    private MessageConverter messageConverter;


    MessageType(int code,MessageConverter messageConverter){
        this.code = code;
        this.messageConverter = messageConverter;
    }

    public int getCode() {
        return code;
    }

    public static MessageType valueOf(int code){
        for (MessageType value : values()) {
            if (value.getCode() == code){
                return value;
            }
        }
        return null;
    }

    public MessageConverter getMessageConverter() {
        return messageConverter;
    }
}
