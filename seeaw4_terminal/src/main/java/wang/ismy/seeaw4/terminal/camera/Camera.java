package wang.ismy.seeaw4.terminal.camera;

import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author MY
 * @date 2019/12/13 17:26
 */
public class Camera {

    public byte[] getCameraSnapshot(){
        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(640,480));
        webcam.open();

        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        try {
            ImageIO.write(webcam.getImage(), "PNG", bos);
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            webcam.close();
        }
        return null;

    }
}
