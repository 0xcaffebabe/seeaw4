package wang.ismy.seeaw4.terminal;




import wang.ismy.seeaw4.terminal.enums.ShellType;
import wang.ismy.seeaw4.terminal.impl.CommonTerminal;
import wang.ismy.seeaw4.terminal.observer.TerminalObserver;
import wang.ismy.seeaw4.terminal.observer.impl.LazyTerminalObserver;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author MY
 * @date 2019/12/13 16:44
 */
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Terminal terminal = new CommonTerminal(ShellType.POWER_SHELL);
        LazyTerminalObserver lazyTerminalObserver = new LazyTerminalObserver();
        terminal.registerObserver(lazyTerminalObserver);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            terminal.input(scanner.nextLine());
        }

    }
}
