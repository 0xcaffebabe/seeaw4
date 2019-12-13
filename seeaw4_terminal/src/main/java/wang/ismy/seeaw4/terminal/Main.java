package wang.ismy.seeaw4.terminal;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import wang.ismy.seeaw4.terminal.camera.Camera;
import wang.ismy.seeaw4.terminal.enums.ShellType;
import wang.ismy.seeaw4.terminal.impl.CommonTerminal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author MY
 * @date 2019/12/13 16:44
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Camera camera = new Camera();
        FileOutputStream fos = new FileOutputStream("d:/photo.png");
        fos.write(camera.getCameraSnapshot());
        fos.close();
    }
}
