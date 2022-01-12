package mvc.servlet;/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */

import mvc.annotion.PpsController;
import mvc.annotion.PpsRequestMapping;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pu PanSheng, 2021/12/29
 * @version OPRA v1.0
 */
public class PpsDispaServlet extends HttpServlet {


    private ConfigurableApplicationContext applicationContext;

    private Map<String,MethodHander> methodHanderMap=new HashMap<>();

    public PpsDispaServlet(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String requestURI = req.getRequestURI();
        String contextPath = req.getContextPath();
        requestURI=requestURI.substring(contextPath.length());
        MethodHander methodHander = methodHanderMap.get(requestURI);
        Map<String, String[]> parameterMap = req.getParameterMap();
        if(methodHander!=null){
            //参数转换
            Object invoke = methodHander.invoke(new Object[]{parameterMap});
            resp.getWriter().write(invoke.toString());
        }else {

            resp.getWriter().flush();
        }

        resp.getWriter().flush();
    }

    @Override
    public void init() throws ServletException {

        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();

        String[] beanNamesForType = beanFactory.getBeanNamesForType(Object.class, true, false);

        for (String s : beanNamesForType) {

            Class<?> type = beanFactory.getType(s);

            if(type.isAnnotationPresent(PpsController.class)){

                for (Method method : type.getMethods()) {

                    if(method.isAnnotationPresent(PpsRequestMapping.class)){
                        PpsRequestMapping annotation = method.getAnnotation(PpsRequestMapping.class);
                        MethodHander methodHander=new MethodHander();
                        methodHander.setMethod(method);
                        methodHander.setT(applicationContext.getBean(s));
                        methodHanderMap.put(annotation.url(), methodHander);
                    }

                }


            }


        }


        System.out.println("servlet init");
    }
}
