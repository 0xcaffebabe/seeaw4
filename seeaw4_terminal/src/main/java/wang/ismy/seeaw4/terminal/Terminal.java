package wang.ismy.seeaw4.terminal;

import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.CharBuffer;

/**
 * 终端
 * @author my
 */
public abstract class Terminal {

    protected TerminalBuffer terminalBuffer = new TerminalBuffer(1024);
    private TerminalObserver terminalObserver;

    /**
     * 向终端输入命令
     * @param cmd 命令
     * @throws IOException
     */
    public abstract void input(String cmd) throws IOException;

    protected void onMessage(char msg){
        terminalObserver.onMessage(msg);
    }

    public void registerObserver(TerminalObserver terminalObserver){
        this.terminalObserver = terminalObserver;
    }

}
