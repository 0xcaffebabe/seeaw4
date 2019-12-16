package wang.ismy.seeaw4.common.promise;

import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.message.chain.impl.PromiseMessageChain;

import java.util.Map;

/**
 * 一个负责异步接收请求结果的类
 *
 * @author MY
 * @date 2019/12/16 21:44
 */
public class ConnectionPromise {
    private static final IdGenerator ID_GENERATOR = new IdGenerator();
    private static final PromiseMessageChain MESSAGE_CHAIN = PromiseMessageChain.getInstance();

    private Message message;
    private SuccessCallback callback;
    private String id;

    public interface SuccessCallback {
        /**
         * 操作成功回调函数
         *
         * @param connection 发送请求的连接
         * @param message    发来的消息
         */
        void success(Connection connection, Message message);
    }

    public ConnectionPromise(Message message) {
        // 包装一下message
        String key = ID_GENERATOR.nextStr();
        this.id = key;
        Map<String, Object> addition = message.addition();
        if (addition != null) {
            addition.put(PromiseMessageChain.PROMISE_CALLBACK,key);
        }else {
            throw new IllegalStateException("消息还未初始化!");
        }

    }

    public ConnectionPromise success(SuccessCallback callback) {
        this.callback = callback;
        return this;
    }

    public String getId() {
        return id;
    }

    public ConnectionPromise async() {
        MESSAGE_CHAIN.registerPromise(this);
        return this;
    }

    public SuccessCallback getCallback() {
        return callback;
    }
}
