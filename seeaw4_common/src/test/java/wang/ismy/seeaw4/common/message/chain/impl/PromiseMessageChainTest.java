package wang.ismy.seeaw4.common.message.chain.impl;

import org.junit.Test;
import org.mockito.Mockito;
import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.promise.ConnectionPromise;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.Assert.*;

public class PromiseMessageChainTest {

    @Test
    public void registerPromise() throws NoSuchFieldException, IllegalAccessException {
        PromiseMessageChain instance = PromiseMessageChain.getInstance();
        ConnectionPromise promise = Mockito.mock(ConnectionPromise.class);
        Mockito.when(promise.getId())
                .thenReturn("id");
        instance.registerPromise(promise);
        Field field = instance.getClass().getDeclaredField("promiseMap");
        field.setAccessible(true);
        Map promiseMap = ((Map) field.get(instance));
        assertEquals(promise,promiseMap.get("id"));

    }

    @Test
    public void process() throws IOException {
        PromiseMessageChain instance = PromiseMessageChain.getInstance();
        ConnectionPromise.SuccessCallback callback = Mockito.mock(ConnectionPromise.SuccessCallback.class);
        ConnectionPromise promise = Mockito.mock(ConnectionPromise.class);
        Mockito.when(promise.getId())
                .thenReturn("id");
        Mockito.when(promise.getCallback())
                .thenReturn(callback);
        instance.registerPromise(promise);
        Connection connection = Mockito.mock(Connection.class);
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.addition())
                .thenReturn(Map.of("promise_callback","id"));

        instance.process(connection,message);

        Mockito.verify(callback).success(connection,message);
    }
}