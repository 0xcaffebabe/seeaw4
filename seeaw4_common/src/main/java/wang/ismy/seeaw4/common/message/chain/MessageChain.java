package wang.ismy.seeaw4.common.message.chain;

import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.message.Message;

import java.io.IOException;

/**
 * 消息处理链，实现该接口，注册到系统，消息到达则会调用
 * @author my
 */
public interface MessageChain {

    /**
     * 当新消息到达时，该方法会被调用
     * @param connection 消息所属的连接
     * @param message　消息
     * @throws IOException　
     */
    void process(Connection connection,Message message) throws IOException;
}
