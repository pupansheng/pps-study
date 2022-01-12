/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com.pps.loadweave;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author Pu PanSheng, 2021/12/28
 * @version OPRA v1.0
 */
@Aspect
public class MyLogAspect3 {


    /**
     * .. 任意子包
     * <p>
     模式	描述
     public * *(..)	任何公共方法的执行
     cn.javass..IPointcutService.*()	   cn.javass包及所有子包下IPointcutService接口中的任何无参方法
     cn.javass..*.*(..)	cn.javass包及所有子包下任何类的任何方法
     cn.javass..IPointcutService.*(*)	   cn.javass包及所有子包下IPointcutService接口的任何只有一个参数方法
     (!cn.javass..IPointcutService+).*(..)	非“cn.javass包及所有子包下IPointcutService接口及子类型”的任何方法
     cn.javass..IPointcutService+.*()	   cn.javass包及所有子包下IPointcutService接口及子类型的的任何无参方法
     </p>
     */
    @Pointcut("execution(* com.pps..Person.*(..))")
    public void modelLayer2() { }

    @Around("modelLayer2()")
    public Object logProfile(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long end=System.currentTimeMillis();

        System.out.println("cost:"+(end-start)+" 毫秒");

        return result;
    }

}
