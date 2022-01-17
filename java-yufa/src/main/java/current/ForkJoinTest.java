package current;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author Pu PanSheng, 2022/1/16
 * @version v1.0
 */
class ForkJoinTask extends RecursiveTask<Integer> {
    Integer start;
    Integer end;

    public ForkJoinTask(Integer start, Integer end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {

        if(end-start>=2){

            ForkJoinTask forkJoinTaskA = new ForkJoinTask(start, (start+end)/2);
            forkJoinTaskA.fork();
            ForkJoinTask forkJoinTaskB= new ForkJoinTask((start+end)/2+1, end);
            forkJoinTaskB.fork();
            return forkJoinTaskA.join()*forkJoinTaskB.join();

        }else {

            return end*start;

        }

    }
}

public class ForkJoinTest {



    public static void main(String args[]) throws ExecutionException, InterruptedException {


        ForkJoinPool forkJoinPool=new ForkJoinPool();

        /* 1 *2*3*4


       1 2    3 4
       2*12
       24
        */

        ForkJoinTask forkJoinTask = new ForkJoinTask(1, 4);


        java.util.concurrent.ForkJoinTask<Integer> submit = forkJoinPool.submit(forkJoinTask);
        Integer integer = submit.get();
        System.out.println(integer);


    }

}
