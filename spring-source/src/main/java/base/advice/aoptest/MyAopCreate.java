/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package base.advice.aoptest;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Pu PanSheng, 2021/12/23
 * @version OPRA v1.0
 */
@Configuration
public class MyAopCreate extends AbstractAutoProxyCreator {
    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName, TargetSource customTargetSource) throws BeansException {


        try {
            Set<String> wrrapM=new HashSet<>();
            Method[] declaredMethods = beanClass.getDeclaredMethods();
            MyTraction declaredAnnotation = beanClass.getDeclaredAnnotation(MyTraction.class);
            if(declaredAnnotation!=null){
                for (Method declaredMethod : declaredMethods) {
                    wrrapM.add(declaredMethod.getName());
                }
            }else {

                for (Method declaredMethod : declaredMethods) {
                    MyTraction annotation = declaredMethod.getDeclaredAnnotation(MyTraction.class);
                    if (annotation != null) {
                        wrrapM.add(declaredMethod.getName());
                    }
                }
            }


            if(!wrrapM.isEmpty()){

                MyTractionAdvice myTractionAdvice = new MyTractionAdvice(wrrapM);
                return new MyTractionAdvice[]{myTractionAdvice};
            }



            return AbstractAutoProxyCreator.DO_NOT_PROXY;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Object[0];
    }
}
