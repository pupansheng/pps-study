package base.advice.aoptest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

/**
 * @author
 * @discription;
 * @time 2021/2/21 14:13
 */

public class AroundAdvice implements MethodInterceptor {


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("环绕：pre");

        Object invoke = invocation.proceed();

        System.out.println("环绕：last");

        return  invoke;
    }
}
