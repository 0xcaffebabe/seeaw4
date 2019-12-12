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
        Runtime runtime = Runtime.getRuntime();
        ProcessBuilder processBuilder = new ProcessBuilder().command("sh");
        processBuilder.redirectError();
        process = processBuilder.start();
        inputStream = process.getInputStream();
        outputStream = process.getOutputStream();
    }

    public void run(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true){
                    try {
                        System.out.write(inputStream.read());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        Scanner scanner =new Scanner(System.in);
        while (scanner.hasNext()){
            try {
                outputStream.write((scanner.nextLine()+"\n").getBytes());
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) throws IOException {
        LinuxTerminal linuxTerminal = new LinuxTerminal();
        linuxTerminal.run();
    }
}
