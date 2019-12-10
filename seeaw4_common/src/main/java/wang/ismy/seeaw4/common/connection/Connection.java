package wang.ismy.seeaw4.common.connection;

import wang.ismy.seeaw4.common.Message;
import wang.ismy.seeaw4.common.listener.MessageListener;

import java.io.IOException;

/**
 * 代表客户与服务器之间的一个逻辑连接
 * @author my
 */
public interface Connection extends AutoCloseable{

	/**
	 * 当调用此方法后，连接将会被关闭
	 * @throws IOException 抛出连接关闭时的IO异常
	 */
	@Override
	void close() throws IOException;

	/**
	 * 获取本连接信息
	 * @return 连接信息
	 */
	ConnectionInfo getInfo();

	/**
	 * 向本连接的对等方发送一条消息
	 * @param message 消息实体
	 * @throws IOException　可能会抛出IO异常
	 */
	void sendMessage(Message message) throws IOException;

	/**
	 * 向该连接绑定一个消息到达监听
	 * @param listener 消息监听对象
	 */
	void bindMessageListener(MessageListener listener);
}
