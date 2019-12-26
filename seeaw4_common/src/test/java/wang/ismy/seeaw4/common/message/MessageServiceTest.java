package wang.ismy.seeaw4.common.message;

import org.junit.Test;
import org.mockito.Mockito;
import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.message.chain.MessageChain;
import wang.ismy.seeaw4.common.message.impl.TextMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class MessageServiceTest {

    @Test
    public void testResolve(){
        MessageService messageService = MessageService.getInstance();
        Map<String,Object> map = new HashMap<>();
        map.put("name","july");
        Message msg = new TextMessage("text".getBytes(),map);
        byte[] build = messageService.build(msg);
        Message resolve = messageService.resolve(build);
        assertEquals(resolve, msg);
    }

    @Test
    public void processChain() throws IOException {
        MessageService messageService = MessageService.getInstance();
        MessageChain mockChain = Mockito.mock(MessageChain.class);
        Connection mockConnection = Mockito.mock(Connection.class);
        Message mockMessage = Mockito.mock(Message.class);
        messageService.registerMessageChain(mockChain);
        messageService.process(mockConnection,mockMessage);
        Mockito.verify(mockChain).process(mockConnection,mockMessage);
    }

    @Test
    public void testEncrypt(){
        byte[] content = new byte[]{1,2,3,4,5};
        MessageService ms = MessageService.getInstance();
        byte[] encode = ms.encode(content);
        assertArrayEquals(content,ms.decode(encode));
    }
}