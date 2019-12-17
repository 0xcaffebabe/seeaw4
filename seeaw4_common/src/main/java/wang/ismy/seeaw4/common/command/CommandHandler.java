package wang.ismy.seeaw4.common.command;

import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.message.impl.CommandMessage;

/**
 * @author MY
 * @date 2019/12/17 9:36
 */
public interface CommandHandler {

    /**
     * 留给子类实现，服务端有服务端的命令处理方式，客户端有客户端的处理方式
     * @param connection 发来消息的连接
     * @param commandMessage 发来的消息
     * @return 回传的消息
     */
    default Message process(Connection connection, CommandMessage commandMessage){
        return null;
    }
}
