package wang.ismy.seeaw4.common;

import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.connection.ConnectionListener;

import java.util.List;

/**
 * 维护连接的功能交由子类实现，子类可根据不同连接类型去用不同的方法维护连接
 * @author my
 */
public interface Connector {

	/**
	 * 绑定连接监听器
	 * @param connectionListener
	 */
	void bindConnectionListener(ConnectionListener connectionListener);

	/**
	 * 获取所有连接
	 * @return 连接列表
	 */
	List<Connection> getConnectionList();
}
