package current;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @author Pu PanSheng, 2022/1/16
 * @version v1.0
 */
public class ForkJoinTest {



    public static void main(String args[]){


        ForkJoinPool forkJoinPool=new ForkJoinPool();


        ForkJoinTask<Integer> forkJoinTask=new ForkJoinTask<Integer>() {
            @Override
            public Integer getRawResult() {
                return null;
            }

            @Override
            protected void setRawResult(Integer value) {

            }

            @Override
            protected boolean exec() {
                return false;
            }
        };

        forkJoinPool.execute(forkJoinTask);









    }

}
