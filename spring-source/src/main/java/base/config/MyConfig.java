/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package base.config;

import base.advice.aoptest.TestService;
import base.entity.P4;
import base.entity.P5;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Pu PanSheng, 2021/12/23
 * @version OPRA v1.0
 */
@Configuration
@Import(P5.class)
public class MyConfig {

    @Bean
    public P4 p4(){
        System.out.println("config");
        return new P4();
    }



}
