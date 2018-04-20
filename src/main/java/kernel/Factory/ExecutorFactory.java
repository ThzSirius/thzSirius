package kernel.Factory;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ExecutorFactory {

    private static ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;
    private static ExecutorService eventExecutor;
    private static ExecutorService handleExecutor;

    public static ScheduledThreadPoolExecutor getScheduledThreadPoolExecutor() {
        if (scheduledThreadPoolExecutor == null) {
              scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(10,new SiriusThreadFactory("srius-schedule-task"));
        }
        return scheduledThreadPoolExecutor;
    }

    public static ExecutorService getEventExecutor(){
        if(eventExecutor == null){
            eventExecutor = Executors.newCachedThreadPool(new SiriusThreadFactory("sirius-event"));

        }
        return eventExecutor;
    }

    public static ExecutorService getHandleExecutor(){
        if(handleExecutor == null){
            handleExecutor = Executors.newCachedThreadPool(new SiriusThreadFactory("http-handle"));
        }
        return handleExecutor;
    }

    public static void clearExecutors(){
        if(scheduledThreadPoolExecutor != null){
            scheduledThreadPoolExecutor.shutdown();
        }
        if(eventExecutor != null){
            eventExecutor.shutdown();
        }
        if(handleExecutor != null){
            handleExecutor.shutdown();
        }
    }

}
