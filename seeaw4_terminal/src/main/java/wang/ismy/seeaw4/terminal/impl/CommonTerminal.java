package wang.ismy.seeaw4.terminal.impl;

import com.github.sarxos.webcam.util.OsUtils;
import sun.awt.SunHints;
import wang.ismy.seeaw4.terminal.Terminal;
import wang.ismy.seeaw4.terminal.camera.Camera;
import wang.ismy.seeaw4.terminal.camera.WebCamera;
import wang.ismy.seeaw4.terminal.desktop.Desktop;
import wang.ismy.seeaw4.terminal.desktop.impl.LinuxDesktop;
import wang.ismy.seeaw4.terminal.desktop.impl.WindowsDesktop;
import wang.ismy.seeaw4.terminal.enums.ShellType;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * @author my
 */
public class CommonTerminal extends Terminal {

    private  Process process;
    private  InputStream inputStream;
    private  OutputStream outputStream;
    private  Camera camera = new WebCamera();
    private  Desktop desktop = new WindowsDesktop();
    private volatile boolean closeFlag = false;

    private final Charset charset = Charset.forName(System.getProperty("sun.jnu.encoding"));

    public CommonTerminal(ShellType type) throws IOException {
        init(type);
    }

    private void init(ShellType type) throws IOException{
        ProcessBuilder processBuilder = new ProcessBuilder()
                .command(type.getShellName())
                .redirectErrorStream(true);
        process = processBuilder.start();
        inputStream = process.getInputStream();
        outputStream = process.getOutputStream();
        switch (OsUtils.getOS()) {
            case WIN:
                desktop = new WindowsDesktop();
                break;
            case NIX:
                desktop = new WindowsDesktop();
                break;
            default:
                desktop = null;
        }

        run();
    }

    public CommonTerminal() throws IOException {
        ShellType shellType;
        if (OsUtils.getOS().equals(OsUtils.WIN)){
            shellType = ShellType.POWER_SHELL;
        }else {
            shellType = ShellType.BASH;
        }
        init(shellType);
    }

    private void run() {

        new Thread((

        ) -> {
            int i = -1;
            // file.encoding 与 sun.jnu.encoding的默认值都是系统编码
            InputStreamReader isr = new InputStreamReader(inputStream, charset);
            try {
                while ((i = isr.read()) != -1 || closeFlag) {
                    // 一旦有输出消息，就会立即发送给观察者
                    onMessage((char) i);
                }
            } catch (IOException e) {
                onError(e);
            }
        }).start();

    }

    @Override
    public void input(String cmd) throws IOException {
        cmd = cmd + "\n";

        ByteBuffer encode = charset.encode(cmd);
        byte[] bytes = new byte[encode.limit()];
        encode.get(bytes);
        outputStream.write(bytes);

        outputStream.flush();
    }

    @Override
    public void close() {
        closeFlag = true;
    }

    @Override
    public Camera getCamera() {
        return camera;
    }

    @Override
    public Desktop getDesktop() {

        return desktop;

    }


}
