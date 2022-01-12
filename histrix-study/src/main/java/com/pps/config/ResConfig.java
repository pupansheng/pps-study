/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com.pps.config;

import feign.Logger;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Pu PanSheng, 2021/12/31
 * @version OPRA v1.0
 */
@Configuration
public class ResConfig {

   // @Bean
    //@LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

}
