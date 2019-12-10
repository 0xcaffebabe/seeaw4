package wang.ismy.seeaw4.common.connection;

/**
 * 连接建立，关闭监听器
 * @author my
 */
public interface ConnectionListener {

    /**
     * 当连接建立后，该方法会被调用
     * @param connection 连接
     */
    void establish(Connection connection);

    /**
     * 当连接关闭时，该方法会被调用
     * @param connection 连接
     */
    void close(Connection connection);
}
