package wang.ismy.seeaw4.common;

import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.connection.OnConnectionCloseListener;
import wang.ismy.seeaw4.common.connection.OnConnectionEstablishListener;

import java.util.List;

/**
 * 维护连接的功能交由子类实现，子类可根据不同连接类型去用不同的方法维护连接
 * @author my
 */
public interface Connector {

	/**
	 * 绑定连接事件
	 * @param listener　连接建立监听器
	 */
	void bindConnectListener(OnConnectionEstablishListener listener);

	/**
	 * 绑定连接断开事件
	 * @param listener　连接断开监听器
	 */
	void bindDisconnectListener(OnConnectionCloseListener listener);

	/**
	 * 获取所有连接
	 * @return 连接列表
	 */
	List<Connection> getConnectionList();
}
