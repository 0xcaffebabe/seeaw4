package wang.ismy.seeaw4.common.message.converter;

import org.junit.Test;
import wang.ismy.seeaw4.common.message.converter.impl.ImgMessageConverter;
import wang.ismy.seeaw4.common.message.converter.impl.TextMessageConverter;

import static org.junit.Assert.*;

public class MessageConverterFactoryTest {

    @Test
    public void imgMessageConverter() {
        assertEquals(ImgMessageConverter.class,MessageConverterFactory.imgMessageConverter().getClass());
    }

    @Test
    public void textMessageConverter() {
        assertEquals(TextMessageConverter.class,MessageConverterFactory.textMessageConverter().getClass());
    }
}