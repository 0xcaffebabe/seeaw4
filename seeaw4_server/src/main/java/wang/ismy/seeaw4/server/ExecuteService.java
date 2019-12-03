package wang.ismy.seeaw4.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author my
 */
public class ExecuteService {

    private ExecutorService executors = Executors.newFixedThreadPool(4);
    private static ExecuteService executeService = new ExecuteService();

    public void excute(Runnable runnable){
        executors.execute(runnable);
    }

    private ExecuteService() { }

    public static  ExecuteService getInstance(){

        return executeService;
    }
}
