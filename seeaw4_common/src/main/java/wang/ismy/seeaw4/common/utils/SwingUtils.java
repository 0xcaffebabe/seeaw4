package wang.ismy.seeaw4.common.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author my
 */
public class SwingUtils {

    public static void showImg(byte[] bytes){
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(bis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(bufferedImage);
        JLabel jLabel = new JLabel(icon);
        JScrollPane panel = new JScrollPane(jLabel);

        JFrame jFrame = new JFrame();
        jFrame.add(panel);
        jFrame.setSize(1366,768);
        jFrame.setVisible(true);
    }
}
