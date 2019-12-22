package wang.ismy.seeaw4.terminal;

import wang.ismy.seeaw4.terminal.camera.Camera;
import wang.ismy.seeaw4.terminal.desktop.Desktop;
import wang.ismy.seeaw4.terminal.observer.TerminalObserver;

import java.io.IOException;

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
        terminalBuffer.append(msg);
        if (terminalObserver != null){
            terminalObserver.onMessage(msg);
        }
    }

    protected void onMessage(String msg){
        for(int i=0;i<msg.length();i++){
            onMessage(msg.charAt(i));
        }
    }

    protected void onError(Throwable throwable){terminalObserver.onError(throwable);}

    public void registerObserver(TerminalObserver terminalObserver){
        this.terminalObserver = terminalObserver;
    }

    /**
     * 获取相机
     * @return 相机
     */
    public abstract Camera getCamera();

    /**
     * 获取桌面
     * @return 桌面
     */
    public abstract Desktop getDesktop();

    public String getTerminalBuffer() {
        return terminalBuffer.getBuffer();
    }

    public void close(){

    }
}
