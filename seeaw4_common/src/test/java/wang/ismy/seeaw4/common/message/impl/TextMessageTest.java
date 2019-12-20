package wang.ismy.seeaw4.common.message.impl;

import org.junit.Before;
import org.junit.Test;
import wang.ismy.seeaw4.common.message.MessageType;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class TextMessageTest {

    private TextMessage textMessage;
    private String str = "test str";
    private Map<String,Object> additions ;
    {
        additions = new HashMap<>();
        additions.put("charset","utf-8");
        additions.put("length",15);
    }

    @Before
    public void before(){
        textMessage = new TextMessage(str.getBytes(),additions);
    }

    @Test
    public void getPayload() {
        assertArrayEquals(str.getBytes(),textMessage.getPayload());
    }

    @Test
    public void messageType() {
        assertEquals(MessageType.TEXT,textMessage.messageType());
    }

    @Test
    public void addition() {
        Map<String,Object> additions = new HashMap<>();
        additions = new HashMap<>();
        additions.put("charset","utf-8");
        additions.put("length",15);
        assertEquals(additions,textMessage.addition());
    }
}