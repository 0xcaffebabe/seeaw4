package wang.ismy.seeaw4.common.message.converter;

import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.message.MessageType;

import java.util.Map;

/**
 *
 * 实现该接口，可以把字节流转为特定的消息
 * @author my
 */
public interface MessageConverter {

    /**
     * 根据传入的参数生成一条消息
     * @param payload 有效载荷
     * @param additions　附加消息
     * @return 具体的消息
     */
    Message convert(byte[] payload, Map<String,Object> additions);

    /**
     * 说明该转换器能转换哪种类型的消息
     * @return
     */
    MessageType messageType();
}
