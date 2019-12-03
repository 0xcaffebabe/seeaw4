package wang.ismy.seeaw4.common.connection;

/**
 * 连接建立监听类
 * @author my
 */
public interface OnConnectionEstablishListener {

	/**
	 * 当连接建立后，该方法会被调用
	 * @param connection 连接
	 */
	void establish(Connection connection);
}
