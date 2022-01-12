import base.advice.aoptest.Config;
import base.advice.aoptest.TestService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author
 * @discription;
 * @time 2020/9/22 9:21
 */
@Configuration
@ComponentScan(basePackages = "base")
//@EnableAspectJAutoProxy
public class Main {


    /**
     * -javaagent:c://pps-agent.jar=plug=c://qpsPlug&qpsClassMatchRegex=com.pps.test**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String args[]) throws InterruptedException {


        ApplicationContext applicationContext=new AnnotationConfigApplicationContext(Main.class);

        TestService testService = applicationContext.getBean(Config.class).testService();
        TestService testService2 = applicationContext.getBean(Config.class).testService();

        System.out.println(testService==testService2);
        /*  ((TestService)applicationContext.getBean("factoryBean2")).fun();*/

        //applicationContext.getBean(TestService.class).fun();
    }

}
