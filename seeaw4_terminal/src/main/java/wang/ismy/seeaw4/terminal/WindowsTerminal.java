package wang.ismy.seeaw4.terminal;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * @author my
 */
public class WindowsTerminal {

    private final Process process;
    private final InputStream inputStream;
    private final OutputStream outputStream;

    public WindowsTerminal() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder()
                .command("cmd")
                .redirectErrorStream(true);
        process = processBuilder.start();
        inputStream = process.getInputStream();
        outputStream = process.getOutputStream();
    }

    public void run() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = -1;
                // file.encoding 与 sun.jnu.encoding的默认值都是系统编码
                Charset charset = Charset.forName(System.getProperty("sun.jnu.encoding"));

                InputStreamReader isr = new InputStreamReader(inputStream,charset);
                try {
                    while ((i = isr.read()) != -1) {
                        System.out.print((char)i);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = -1;
                try {
                    while ((i = System.in.read()) != -1) {
                        outputStream.write(i);
                        outputStream.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public static void main(String[] args) throws IOException {
        WindowsTerminal windowsTerminal = new WindowsTerminal();
        windowsTerminal.run();
    }
}
