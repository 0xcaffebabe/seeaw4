package com.example.seeaw4.seeaw4.android;

import org.junit.Test;

import wang.ismy.seeaw4.common.message.MessageService;

import static org.junit.Assert.assertArrayEquals;

public class EncryptTest {
    @Test
    public void testEncrypt(){
        byte[] content = new byte[]{1,2,3,4,5};
        MessageService ms = MessageService.getInstance();
        byte[] encode = ms.encode(content);
        assertArrayEquals(content,ms.decode(encode));
    }
}
