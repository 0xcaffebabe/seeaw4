package wang.ismy.seeaw4.terminal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

/**
 * @author my
 */
public class LinuxTerminal {

    private final Process process;
    private final InputStream inputStream;
    private final OutputStream outputStream;

    public LinuxTerminal() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder()
                .command("bash")
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
                try {
                    while ((i = inputStream.read()) != -1) {
                        System.out.write(i);
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
        LinuxTerminal linuxTerminal = new LinuxTerminal();
        linuxTerminal.run();
    }
}
