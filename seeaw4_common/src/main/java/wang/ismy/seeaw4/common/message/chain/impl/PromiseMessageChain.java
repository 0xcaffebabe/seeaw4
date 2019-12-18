package wang.ismy.seeaw4.common.message.chain.impl;

import wang.ismy.seeaw4.common.promise.ConnectionPromise;
import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.message.chain.MessageChain;
import wang.ismy.seeaw4.common.utils.CollectionUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 一个回调控制对象，通过该对象包装消息
 * 该对象会向消息写入一个回调ID，这样，当外部消息发进来时，如果外部消息带有回调ID
 * 则该对象的回调函数会被调用
 * @author MY
 * @date 2019/12/16 21:52
 */
public class PromiseMessageChain implements MessageChain {

    private static final PromiseMessageChain INSTANCE = new PromiseMessageChain();
    public static final String PROMISE_CALLBACK = "promise_callback";

    private Map<String, ConnectionPromise> promiseMap = new ConcurrentHashMap<>();

    private PromiseMessageChain() { }

    public void registerPromise(ConnectionPromise promise){
        String id = promise.getId();
        promiseMap.put(id,promise);
    }

    public static PromiseMessageChain getInstance(){
        return INSTANCE;
    }

    @Override
    public void process(Connection connection, Message message) throws IOException {
        Map<String, Object> addition = message.addition();
        // 当接收到的消息含有回调ID时，那么，在这里调用相关的回调方法
        if (!CollectionUtils.isEmpty(addition)) {
            Object o = addition.get(PROMISE_CALLBACK);
            if (o != null) {
                String key = o.toString();
                ConnectionPromise promise = promiseMap.get(key);
                if (promise != null){
                    promise.getCallback().success(connection,message);
                }
            }
        }
    }
}
