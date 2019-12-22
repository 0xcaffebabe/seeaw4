package wang.ismy.seeaw4.common;

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

    public void close(){
        executors.shutdown();
    }

    public static void excutes(Runnable runnable){
        executeService.excute(runnable);
    }
}
