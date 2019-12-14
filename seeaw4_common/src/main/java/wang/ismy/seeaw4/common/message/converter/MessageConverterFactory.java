package wang.ismy.seeaw4.common.message.converter;

import wang.ismy.seeaw4.common.message.converter.impl.CommandMessageConverter;
import wang.ismy.seeaw4.common.message.converter.impl.ImgMessageConverter;
import wang.ismy.seeaw4.common.message.converter.impl.TextMessageConverter;

/**
 * 消息转换器工厂，负责生产各种消息转换器
 * @author my
 */
public class MessageConverterFactory {

    public static MessageConverter imgMessageConverter(){
        return new ImgMessageConverter();
    }

    public static MessageConverter textMessageConverter(){
        return new TextMessageConverter();
    }

    public static MessageConverter commandMessageConverter(){
        return new CommandMessageConverter();
    }
}
