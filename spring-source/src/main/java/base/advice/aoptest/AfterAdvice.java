package base.advice.aoptest;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * @author
 * @discription;
 * @time 2021/2/21 13:51
 */
public class AfterAdvice implements AfterReturningAdvice {

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("return:"+returnValue);

    }
}
