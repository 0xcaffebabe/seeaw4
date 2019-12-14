package wang.ismy.seeaw4.terminal;


import wang.ismy.seeaw4.terminal.desktop.impl.WindowsDesktop;
import wang.ismy.seeaw4.terminal.enums.ImgType;
import wang.ismy.seeaw4.terminal.enums.ShellType;
import wang.ismy.seeaw4.terminal.impl.CommonTerminal;
import wang.ismy.seeaw4.terminal.observer.TerminalObserver;
import wang.ismy.seeaw4.terminal.observer.impl.LazyTerminalObserver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author MY
 * @date 2019/12/13 16:44
 */
public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        WindowsDesktop desktop = new WindowsDesktop();
        FileOutputStream fos = new FileOutputStream("/home/my/desktop.jpg");
        byte[] screen = desktop.getScreen(ImgType.JPEG, new Resolution(1366, 768));
        fos.write(screen);
        fos.close();

//        Terminal terminal = new CommonTerminal(ShellType.BASH);
//        LazyTerminalObserver lazyTerminalObserver = new LazyTerminalObserver();
//        terminal.registerObserver(lazyTerminalObserver);
//        Scanner scanner = new Scanner(System.in);
//        while (scanner.hasNextLine()) {
//            terminal.input(scanner.nextLine());
//        }

    }
}
