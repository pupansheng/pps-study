package base.advice.aoptest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.framework.adapter.AdvisorAdapterRegistry;
import org.springframework.aop.framework.adapter.GlobalAdvisorAdapterRegistry;

/**
 * @author
 * @discription;
 * @time 2021/2/21 13:46
 */
public class Test {
    private static AdvisorAdapterRegistry advisorAdapterRegistry = GlobalAdvisorAdapterRegistry.getInstance();
    public static void main(String[] args) throws Exception {
       //用cglib 方式代理对象
        ProxyFactory pf2 = new ProxyFactory(new Controller());
        pf2.addAdvice(new AroundAdvice());
        Object proxy1 = pf2.getProxy();
        Controller controller=(Controller)proxy1;
        controller.test();;

        //1.初始化源对象(一定要实现接口)
        TestService target =new TestServiceImpl();
        //2.AOP 代理工厂
        ProxyFactory pf = new ProxyFactory(target);



       // Advisor wrap = advisorAdapterRegistry.wrap(new AroundAdvice());
        //3.装配Advice

      //  pf.addAdvice(new AfterAdvice());
        pf.setExposeProxy(true);


        MethodInterceptor testMe=new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                System.out.println("qian");
                Object r=invocation.proceed();
                System.out.println("after");
                return r;
            }
        };
        pf.addAdvice(testMe);


        ////4.获取代理对象
        TestService proxy =(TestService)pf.getProxy();

        //5.调用业务
        //proxy.fun();

        //方式二
        ProxyFactoryBean proxyFactoryBean=new ProxyFactoryBean();
        proxyFactoryBean.setProxyInterfaces(new Class[]{TestService.class});
        proxyFactoryBean.setTarget(target);
        proxyFactoryBean.setInterceptorNames();
        proxyFactoryBean.addAdvice(new AfterAdvice());
        Object object = proxyFactoryBean.getObject();
        TestService object1 = (TestService) object;
        //object1.fun();




    }
}
