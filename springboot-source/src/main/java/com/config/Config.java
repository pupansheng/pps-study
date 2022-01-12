/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com.config;

import com.entity.P1;
import com.entity.P2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Pu PanSheng, 2022/1/7
 * @version OPRA v1.0
 */
@Configuration
public class Config {



    @Bean
    public P1 f1(){

        System.out.println("f1----------");
        P2 p2 = f2();
        return new P1();
    }

    @Bean
    public P2 f2(){

        System.out.println("f2----------");
        return new P2();
    }
    @Bean
    public P2 f3(){

        System.out.println("f3----------");
        return f2();
    }
}
