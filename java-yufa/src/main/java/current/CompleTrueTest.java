package current;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Pu PanSheng, 2022/1/16
 * @version v1.0
 */

class Person{

    public int time=100;


    public int timeOut=6000;

    public String r;

    @Override
    public String toString() {
        return "Person{" +
                "time=" + time +
                ", timeOut=" + timeOut +
                ", r='" + r + '\'' +
                '}';
    }
}
public class CompleTrueTest {



    @Test
    public void f1() throws ExecutionException, InterruptedException {


        CompletableFuture<Person> completableFuture=CompletableFuture.supplyAsync(()->{
            Person p=new Person();
            p.time=9000;
            System.out.println("收到任务 处理中---："+p.time);
            try {
                Thread.sleep(p.time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("处理结束");
            p.r="sucess";
            return p;
        });

        CompletableFuture<Person> completableFuture2=  CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("eroor");
            completableFuture.completeExceptionally(new RuntimeException("超时"+400));
            return null;
        });/*.exceptionally(e->{
            Person person=new Person();
            person.r="fail:"+e;
            return person;
        });*/




        Person person = completableFuture.get();

        System.out.println(person);

    }


    @Test
    public void f2() throws IOException {


        TimeOutRuner.runWithTimeOut(()->{

            System.out.println("启动1");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("end1");

        },2000);

        TimeOutRuner.runWithTimeOut(()->{

            System.out.println("启动2");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("end2");

        },2000);



       // System.in.read();
    }

    @Test
    public void f3() {


        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);


        executorService.schedule(()->{
            System.out.println("3000");
        },3000, TimeUnit.MILLISECONDS);

        while (true){

            executorService.schedule(()->{
                System.out.println("1500");
            },1500,TimeUnit.MILLISECONDS);

        }

    }
}
