/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com.pps.boot.context;

import com.pps.boot.listener.ServletInit;
import com.pps.boot.web.WebServer;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * @author Pu PanSheng, 2021/12/29
 * @version OPRA v1.0
 */
public class PpsWebApplicationContext extends AnnotationConfigApplicationContext {


    private Tomcat tomcat;


    @Override
    protected void onRefresh() throws BeansException {

        try {
            Tomcat tomcat = WebServer.create(this, getServletInits());
            this.tomcat=tomcat;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void finishRefresh() {
        super.finishRefresh();

        new Thread(()->{

            try {
                tomcat.init();
                tomcat.start();
                tomcat.getServer().await();
            } catch (LifecycleException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

        }).start();


    }

    protected ServletInit[] getServletInits(){


        String[] beanNamesForType = getBeanNamesForType(ServletInit.class);

        ServletInit[] servletInits=new ServletInit[beanNamesForType.length];

        for (int i = 0; i < beanNamesForType.length; i++) {
            servletInits[i]=getBean(beanNamesForType[i], ServletInit.class);
        }

        servletInits=Arrays.stream(servletInits).sorted(Comparator.comparing(ServletInit::sort))
                .collect(Collectors.toSet()).toArray(new ServletInit[0]);

        return servletInits;
    }



}
