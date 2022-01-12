/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package mvc.servlet;


import com.pps.boot.listener.ServletInit;
import org.springframework.context.ConfigurableApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * @author Pu PanSheng, 2021/12/29
 * @version OPRA v1.0
 */
public class MvcServletInit implements ServletInit {
    @Override
    public void startUp(ServletContext servletContext) {

        PpsDispaServlet ppsDispaServlet=new PpsDispaServlet((ConfigurableApplicationContext)
                servletContext.getAttribute("pps-context"));
        //启动就加载
        ServletRegistration.Dynamic dipa = servletContext.addServlet("dipa", ppsDispaServlet);
        //dipa.setLoadOnStartup(1);
        dipa.addMapping("/*");

    }
}
