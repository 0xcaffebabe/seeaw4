package wang.ismy.seeaw4.common.message;

import org.junit.Test;
import wang.ismy.seeaw4.common.message.converter.impl.TextMessageConverter;

import static org.junit.Assert.*;

public class MessageTypeTest {

    @Test
    public void valueOf() {
        assertEquals(MessageType.TEXT,MessageType.valueOf(0));
        assertEquals(MessageType.IMG,MessageType.valueOf(1));
    }

    @Test
    public void getMessageConverter() {
        assertEquals(TextMessageConverter.class,MessageType.TEXT.getMessageConverter().getClass());
    }
}