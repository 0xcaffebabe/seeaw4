package wang.ismy.seeaw4.common.connection;

/**
 * 连接关闭监听器
 * @author my
 */
public interface OnConnectionCloseListener {

	/**
	 * 当连接关闭时，该方法会被调用
	 * @param connection 连接
	 */
	void close(Connection connection);
}
