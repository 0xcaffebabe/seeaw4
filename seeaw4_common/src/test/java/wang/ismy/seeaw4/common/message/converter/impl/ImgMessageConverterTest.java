package wang.ismy.seeaw4.common.message.converter.impl;

import org.junit.Test;
import wang.ismy.seeaw4.common.message.Message;
import wang.ismy.seeaw4.common.message.MessageService;
import wang.ismy.seeaw4.common.message.impl.ImgMessage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ImgMessageConverterTest {

    @Test
    public void test() throws AWTException, IOException {
        BufferedImage screenCapture = new Robot().createScreenCapture(new Rectangle(1920, 1080));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(screenCapture,"JPG",bos);
        byte[] bytes = bos.toByteArray();
        ImgMessage imgMessage = new ImgMessage(bytes,"JPG");
        MessageService ms = MessageService.getInstance();
        byte[] build = ms.build(imgMessage);
        Message resolve = ms.resolve(build);
        System.out.println(Arrays.toString(resolve.getPayload()));
        assertTrue(resolve instanceof ImgMessage);
    }

}