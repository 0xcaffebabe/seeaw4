package wang.ismy.seeaw4.terminal;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 唯一任务执行服务
 * 创建任务时，传入ID与执行体
 * 任务执行完毕前，重复添加的任务将会被忽略
 * @author MY
 * @date 2019/12/14 16:05
 */
public class TerminalExecuteService {

    private ExecutorService executors = Executors.newFixedThreadPool(4);
    private static final TerminalExecuteService INSTANCE = new TerminalExecuteService();
    private Map<String,Boolean> executeTable = new ConcurrentHashMap<>();

    private TerminalExecuteService() {
    }

    public void inform(String id, Runnable runnable){
        if (executeTable.containsKey(id)){
            // 如果还在，代表上一次的任务还没执行，直接返回
            return;
        }
        executeTable.put(id,true);
        executors.submit(()->{

            runnable.run();
            executeTable.remove(id);
        });
    }

    public static TerminalExecuteService getInstance(){
        return INSTANCE;
    }
}
