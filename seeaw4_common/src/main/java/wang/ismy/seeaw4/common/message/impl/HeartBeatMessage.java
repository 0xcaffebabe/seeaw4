package wang.ismy.seeaw4.common.message.impl;

import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.message.MessageType;

/**
 * 代表一次心跳
 * @author my
 */
public class HeartBeatMessage extends Message {
    @Override
    public MessageType messageType() {
        return MessageType.HEART_BEAT;
    }
}
