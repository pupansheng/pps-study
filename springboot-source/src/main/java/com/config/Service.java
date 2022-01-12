/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com.config;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author Pu PanSheng, 2022/1/7
 * @version OPRA v1.0
 */
@Component
public class Service {


    public String f1(){


        f2();;

        return "f2";

    }


    @Async
    public void f2(){

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("f2");
    }

}
