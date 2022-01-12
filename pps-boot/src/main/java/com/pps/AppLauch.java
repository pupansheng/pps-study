/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com.pps;


import java.lang.reflect.Method;

/**
 * @author Pu PanSheng, 2021/12/28
 * @version OPRA v1.0
 */
public class AppLauch {

    public static void main(String args[]) throws Exception {

        String main="com.pps.spring.AppTest";
        MyLoader myLoader=MyLoader.getLoader("C:\\qpsPlug");
        Thread.currentThread().setContextClassLoader(myLoader);
        Class<?> aClass = Thread.currentThread().getContextClassLoader().loadClass(main);
        String[] strings = {};
        for (Method method : aClass.getMethods()) {
            if(method.getName().equals("main")){
                method.invoke(null, (Object) strings);
                return;
            }
        }



    }
}
