package wang.ismy.seeaw4.terminal.camera;

import com.github.sarxos.webcam.Webcam;
import wang.ismy.seeaw4.terminal.Resolution;
import wang.ismy.seeaw4.terminal.enums.ImgType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author MY
 * @date 2019/12/13 17:26
 */
public class WebCamera implements Camera {

    @Override
    public synchronized byte[] getCameraSnapshot(ImgType imgType, Resolution resolution){
        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(resolution.getWidth(),resolution.getHeight()));
        webcam.open();
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        try {
            ImageIO.write(webcam.getImage(), imgType.getFormat(), bos);
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            webcam.close();
        }
        return null;
    }


}
