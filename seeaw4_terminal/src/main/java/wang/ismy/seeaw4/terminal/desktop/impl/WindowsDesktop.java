package wang.ismy.seeaw4.terminal.desktop.impl;

import wang.ismy.seeaw4.terminal.Resolution;
import wang.ismy.seeaw4.terminal.desktop.Desktop;
import wang.ismy.seeaw4.terminal.enums.ImgType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 桌面
 *
 * @author MY
 * @date 2019/12/14 14:47
 */
public class WindowsDesktop implements Desktop {

    @Override
    public synchronized byte[] getScreen(ImgType imgType, Resolution resolution) {
        BufferedImage bfImage = null;

        try {
            Robot robot = new Robot();
            bfImage = robot.createScreenCapture(new Rectangle(0, 0, resolution.getWidth(), resolution.getHeight()));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bfImage, imgType.getFormat(), byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException | AWTException e) {
            e.printStackTrace();
        }
        return null;
    }
}
