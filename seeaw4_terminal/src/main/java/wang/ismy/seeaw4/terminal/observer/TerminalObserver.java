package wang.ismy.seeaw4.terminal.observer;

/**
 * @author MY
 * @date 2019/12/13 16:38
 */
public interface TerminalObserver {

    /**
     * 当终端产生消息时，会调用该方法
     * @param msg
     */
    void onMessage(char msg);

    /**
     * 当终端发生错误，会调用该方法
     * @param throwable
     */
    void onError(Throwable throwable);
}
