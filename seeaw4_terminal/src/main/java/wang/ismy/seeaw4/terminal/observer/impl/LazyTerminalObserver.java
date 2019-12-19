package wang.ismy.seeaw4.terminal.observer.impl;

import wang.ismy.seeaw4.terminal.TerminalExecuteService;
import wang.ismy.seeaw4.terminal.observer.TerminalObserver;

/**
 * 懒惰式观察者，当消息积累一定长度或时间才会客户才会取到消息
 *
 * @author MY
 * @date 2019/12/14 15:47
 */
public abstract class LazyTerminalObserver implements TerminalObserver {

    private static final int DELAY = 200;
    private static final int MIN_LENGTH = 10;
    private static final String ID = "0xcaffebabe-lazyTerminalObserver";
    private TerminalExecuteService executeService = TerminalExecuteService.getInstance();

    private final StringBuffer sb = new StringBuffer();


    @Override
    public void onMessage(char msg) {
        sb.append(msg);
        executeService.inform(ID, () -> {
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String ret = null;
            synchronized (sb) {
                ret = sb.toString();
                sb.delete(0, ret.length());
            }

            onMessage(ret);
        });
    }

    public abstract void onMessage(String msg);

    @Override
    public void onError(Throwable throwable) {

    }
}
