/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com.pps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Pu PanSheng, 2021/12/31
 * @version OPRA v1.0
 */
@SpringBootApplication
@EnableFeignClients("com.pps.fegin")
public class App {


    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }


}
