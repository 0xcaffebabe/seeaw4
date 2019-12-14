package wang.ismy.seeaw4.common.promise;

/**
 * @author MY
 * @date 2019/12/14 17:34
 */
public class Promise {

    private PromiseState promiseState;

    public interface PromiseRunnable {
        void run();
    }

    public Promise(PromiseRunnable runnable) {

    }

    private void success(){
        promiseState = PromiseState.SUCCESS;
    }

    private void fail(){
        promiseState = PromiseState.FAIL;
    }


}
