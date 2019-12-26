package wang.ismy.seeaw4.common.connection;

/**
 * 连接状态更改监听器
 * @author my
 */
public interface ConnectionStateChangeListener {

    /**
     * 当连接状态发生改变，该方法会被调用
     * @param connection
     * @param state
     */
    void onChange(Connection connection,ConnectionState state);
}
