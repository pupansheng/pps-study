package current;

import java.util.Date;
import java.util.concurrent.*;

/**
 * @author Pu PanSheng, 2022/1/16
 * @version v1.0
 */
public class TimeOutRuner {

    private static ScheduledExecutorService executorService= Executors.newScheduledThreadPool(1);


    public static void runWithTimeOut(Runnable runnable, int timeOut)  {

        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(runnable);

        executorService.schedule(()->{
            voidCompletableFuture.completeExceptionally(new RuntimeException("超时:"+new Date()));
        },timeOut, TimeUnit.MILLISECONDS);

        try {
            voidCompletableFuture.get();
        } catch (Exception e){
            throw new RuntimeException(e);
        }

    }

}
