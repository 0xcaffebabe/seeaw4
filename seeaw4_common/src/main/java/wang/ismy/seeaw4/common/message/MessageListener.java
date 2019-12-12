package wang.ismy.seeaw4.common.message;

import wang.ismy.seeaw4.common.connection.Connection;

/**
 * 消息到达监听器
 * @author my
 */
public interface MessageListener {

	/**
	 * 消息到到达会调用这个方法
	 * @param connection 消息所属的连接
	 * @param message 消息
	 */
	void onMessage(Connection connection, Message message);
}
