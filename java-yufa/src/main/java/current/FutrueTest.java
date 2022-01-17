/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package current;

import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.*;

/**
 * @author Pu PanSheng, 2022/1/10
 * @version OPRA v1.0
 */
public class FutrueTest {



    @Test
    public void test() throws ExecutionException, InterruptedException, IOException {


        FutureTask<String> futureTask=new FutureTask<>(()->{


            for (int i = 0; i < 6; i++) {

                Thread.sleep(3000);
                System.out.println("jishi:  "+i);
            }



            return  "123";});

        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(1,1,1,TimeUnit.SECONDS,new ArrayBlockingQueue<>(1));

        threadPoolExecutor.submit(futureTask);

        String s = futureTask.get();
        System.out.println(s);


        System.in.read();
    }




}
