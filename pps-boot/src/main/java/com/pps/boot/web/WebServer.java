/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com.pps.boot.web;


import com.pps.boot.listener.ServletInit;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Pu PanSheng, 2021/12/29
 * @version OPRA v1.0
 */
public class WebServer {


    public static Tomcat create(GenericApplicationContext appContext, ServletInit... servletInits) {

        Tomcat tomcat = new Tomcat();
        ConfigurableEnvironment environment = appContext.getEnvironment();
        tomcat.setPort(environment.getProperty("servlet.port", Integer.class));
        Context context = tomcat.addContext(environment.getProperty("servlet.context"), null);

        context.addServletContainerInitializer(new ServletContainerInitializer() {
            @Override
            public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {

                servletContext.setAttribute("pps-context", appContext);

                for (ServletInit servletInit : servletInits) {
                    servletInit.startUp(servletContext);
                }

            }
        }, new HashSet<>());

        return tomcat;


    }

}
