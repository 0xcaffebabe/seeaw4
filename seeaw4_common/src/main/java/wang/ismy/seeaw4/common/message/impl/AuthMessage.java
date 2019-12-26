package wang.ismy.seeaw4.common.message.impl;

import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.message.MessageType;

import java.util.Map;

/**
 * @author MY
 * @date 2019/12/25 23:58
 */
public class AuthMessage extends Message {

    public AuthMessage(byte[] payload, Map<String, Object> addition) {
        super(payload, addition);
    }

    public AuthMessage() {
        super();
    }

    public String getPassword(){
        Object password = getAddition("password");
        if (password != null){
            return password.toString();
        }
        return null;

    }

    @Override
    public MessageType messageType() {
        return MessageType.AUTH;
    }
}
