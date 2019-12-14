package wang.ismy.seeaw4.common.message.impl;

import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.message.MessageType;
import wang.ismy.seeaw4.common.message.chain.MessageChain;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 该种消息一般被用来客户端命令服务端操作某些事
 * @author MY
 * @date 2019/12/14 17:44
 */
public class CommandMessage extends Message {

    public CommandMessage(byte[] payload, Map<String, Object> addition) {
        super(payload, addition);
    }

    public CommandMessage(){
        super();

    }

    @Override
    public MessageType messageType() {
        return MessageType.COMMAND;
    }

    public String getCommand(){
        return addition.get("cmd").toString();
    }

    public void setCommand(String cmd){
        addition.put("cmd",cmd);
    }
}
