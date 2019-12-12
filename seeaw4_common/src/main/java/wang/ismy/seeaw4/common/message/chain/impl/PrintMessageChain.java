package wang.ismy.seeaw4.common.message.chain.impl;

import lombok.extern.slf4j.Slf4j;
import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.message.chain.MessageChain;

import java.io.IOException;

/**
 * 该链只会打印消息
 *
 * @author my
 */
@Slf4j
public class PrintMessageChain implements MessageChain {

    @Override
    public void process(Connection connection, Message message) throws IOException {
        log.info("接受到消息，发送者:{},消息类型是:{},消息内容:{}", connection, message.messageType(), message);
    }
}
