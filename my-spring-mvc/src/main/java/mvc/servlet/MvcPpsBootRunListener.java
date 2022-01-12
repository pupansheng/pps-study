/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package mvc.servlet;


import com.pps.boot.listener.PpsBootRunListener;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Pu PanSheng, 2021/12/29
 * @version OPRA v1.0
 */
public class MvcPpsBootRunListener implements PpsBootRunListener {
    @Override
    public void preEnv(ConfigurableEnvironment environment) {

        MutablePropertySources propertySources = environment.getPropertySources();
        Map map=new HashMap<>();
        map.put("servlet.port","8090");
        map.put("servlet.context","/pps");
        MapPropertySource mapPropertySource=new MapPropertySource("pps",map);
        propertySources.addFirst(mapPropertySource);

    }

    @Override
    public void contextPre(ConfigurableApplicationContext context) {

        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        if(beanFactory instanceof BeanDefinitionRegistry){
            BeanDefinitionRegistry beanFactory1 = (BeanDefinitionRegistry) beanFactory;
            BeanDefinition genericBeanDefinition=new GenericBeanDefinition();
            genericBeanDefinition.setBeanClassName("mvc.servlet.MvcServletInit");
            beanFactory1.registerBeanDefinition("mvcServletInit", genericBeanDefinition);
        }

    }

    @Override
    public void freshAfter(ConfigurableApplicationContext context) {

    }
}
