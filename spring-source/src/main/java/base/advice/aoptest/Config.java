/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package base.advice.aoptest;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * cglib 代理  直接调用本类的方法 还是走的代理 jdk则不可以
 * @author Pu PanSheng, 2021/12/24
 * @version OPRA v1.0
 */
@Configuration
public class Config {


    /**
     * 原始对象
     * @return
     */
    @Bean
    public TestService testService(){

        System.out.println("testImpl--------------");
        aroundAdvice();
        aroundAdvice();
        return new TestServiceImpl();

    }

    /**
     * 切面
     * @return
     */
   @Bean
   public AroundAdvice aroundAdvice(){

       System.out.println("aroundAdvices---");
        return new AroundAdvice();

   }

    /**
     * advice 的升级 它可以匹配一个类的具体方法  相当于把advice包装了一下
     * @return
     */
   @Bean
   public NameMatchMethodPointcutAdvisor myAdvisor(AroundAdvice aroundAdvice){

       NameMatchMethodPointcutAdvisor nameMatchMethodPointcutAdvisor=new NameMatchMethodPointcutAdvisor();

       //设置切面
       nameMatchMethodPointcutAdvisor.setAdvice(aroundAdvice);
       //方法名
       nameMatchMethodPointcutAdvisor.setMappedName("fun");

       return nameMatchMethodPointcutAdvisor;

   }

    /**
     * 方法一    利用 ProxyFactoryBean  + advice   但是只能匹配一个内的全部方法 且只能这样用
     *  ((TestService)applicationContext.getBean("proxyFactoryBean")).fun();
     * @param testService
     * @return
     */

    //@Bean
    public ProxyFactoryBean proxyFactoryBean(TestService testService){
        ProxyFactoryBean proxyFactoryBean=new ProxyFactoryBean();
        proxyFactoryBean.setInterceptorNames("aroundAdvice");
        proxyFactoryBean.setTarget(testService);
        return proxyFactoryBean;
    }

    /**
     * 方法2    利用 ProxyFactoryBean + advisor  精确匹配具体的方法
     *  ((TestService)applicationContext.getBean("proxyFactoryBean2")).fun();
     * @param testService
     * @return
     */

    //@Bean
    public ProxyFactoryBean proxyFactoryBean2(TestService testService){
        ProxyFactoryBean proxyFactoryBean=new ProxyFactoryBean();
        proxyFactoryBean.setInterceptorNames("aroundAdvice");
        proxyFactoryBean.setTarget(testService);
        return proxyFactoryBean;
    }


    /**
     * 方式三  把容器中的真实对象 替换成代理对象
     * 它会收集 容器中的advisor 然后匹配容器中的对象然后代理 然后替换
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator autoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();

        return defaultAdvisorAutoProxyCreator;
    }



}
