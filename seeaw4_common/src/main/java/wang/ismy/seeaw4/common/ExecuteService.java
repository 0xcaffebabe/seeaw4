package wang.ismy.seeaw4.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author my
 */
public class ExecuteService {

    private ExecutorService executors = Executors.newFixedThreadPool(8);
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(8);

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

    public static void schedule(Runnable runnable,int interval){
        executeService.scheduledExecutorService.scheduleAtFixedRate(runnable,10,interval, TimeUnit.SECONDS);
    }
}
