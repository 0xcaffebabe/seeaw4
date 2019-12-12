package wang.ismy.seeaw4.common.message.chain.impl;

import org.junit.Test;
import org.mockito.Mockito;
import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.message.Message;

import java.io.IOException;

import static org.junit.Assert.*;

public class PrintMessageChainTest {

    @Test
    public void process() throws IOException {
        PrintMessageChain chain = new PrintMessageChain();

        Connection mockConnection = Mockito.mock(Connection.class);
        Message mockMessage = Mockito.mock(Message.class);
        chain.process(mockConnection,mockMessage);
        Mockito.verify(mockMessage).messageType();

    }
}