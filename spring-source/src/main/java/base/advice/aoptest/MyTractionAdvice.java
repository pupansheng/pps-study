/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package base.advice.aoptest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Pu PanSheng, 2021/12/23
 * @version OPRA v1.0
 */
public class MyTractionAdvice implements MethodInterceptor {

    public Set<String> set;
    public MyTractionAdvice(Set<String> stringSet){
        set=stringSet;
    }
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {


        String name = invocation.getMethod().getName();
        if(!set.contains(name)){
            return invocation.proceed();
        }

        Instant now = Instant.now();
        Object proceed = invocation.proceed();
        Instant now1 = Instant.now();
        System.out.println(name +" 执行时间："+ Duration.between(now,now1).toMillis()+"毫秒");
        return proceed;
    }
}
