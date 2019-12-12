package wang.ismy.seeaw4.common.message;

import org.junit.Test;
import org.mockito.Mockito;
import wang.ismy.seeaw4.common.connection.Connection;
import wang.ismy.seeaw4.common.message.chain.MessageChain;
import wang.ismy.seeaw4.common.message.impl.TextMessage;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.*;

public class MessageServiceTest {

    @Test
    public void build() {
        MessageService messageService = MessageService.getInstance();
        Message mockMsg = Mockito.mock(Message.class);
        Mockito.when(mockMsg.getPayload())
                .thenReturn(new byte[]{1,1});
        Mockito.when(mockMsg.addition())
                .thenReturn(null);
        Mockito.when(mockMsg.messageType())
                .thenReturn(MessageType.TEXT);
        // 前4个字节代表消息类型　5-8个字节代表附加消息偏移量　9-偏移量+1代表附加消息　最后一部分是有效载荷
        byte[] expectedBytes = new byte[]{0,0,0,0,0,0,0,0,1,1};
        byte[] build = messageService.build(mockMsg);
        assertArrayEquals(expectedBytes,build);
    }

    @Test
    public void resolve(){
        MessageService messageService = MessageService.getInstance();
        Message msg = new TextMessage("text", Map.of("name","july"));
        byte[] build = messageService.build(msg);
        Message resolve = messageService.resolve(build);
        assertTrue(resolve.equals(msg));
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
}