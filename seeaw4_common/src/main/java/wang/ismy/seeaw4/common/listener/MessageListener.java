package wang.ismy.seeaw4.common.listener;

import wang.ismy.seeaw4.common.Message;

/**
 * 消息到达监听器
 * @author my
 */
public interface MessageListener {

	/**
	 * 消息到到达会调用这个方法
	 * @param message 消息
	 */
	void onMessageArrival(Message message);
}
