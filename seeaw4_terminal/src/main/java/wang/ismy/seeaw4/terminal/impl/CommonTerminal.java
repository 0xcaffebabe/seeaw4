package wang.ismy.seeaw4.terminal.impl;

import wang.ismy.seeaw4.terminal.Terminal;
import wang.ismy.seeaw4.terminal.enums.ShellType;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * @author my
 */
public class CommonTerminal extends Terminal {

    private final Process process;
    private final InputStream inputStream;
    private final OutputStream outputStream;


    public CommonTerminal(ShellType type) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder()
                .command(type.getShellName())
                .redirectErrorStream(true);
        process = processBuilder.start();
        inputStream = process.getInputStream();
        outputStream = process.getOutputStream();
        run();
    }



    private void run() {

        new Thread(() -> {
            int i = -1;
            // file.encoding 与 sun.jnu.encoding的默认值都是系统编码
            Charset charset = Charset.forName(System.getProperty("sun.jnu.encoding"));
            InputStreamReader isr = new InputStreamReader(inputStream,charset);
            try {
                while ((i = isr.read()) != -1) {
                    // 一旦有输出消息，就会立即发送给观察者
                    CommonTerminal.super.onMessage((char)i);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    @Override
    public void input(String cmd) throws IOException {
        outputStream.write(cmd.getBytes());
        outputStream.flush();
    }
}
