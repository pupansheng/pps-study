/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com;

import com.config.Config;
import com.config.Service;
import com.entity.P1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pu PanSheng, 2021/12/29
 * @version OPRA v1.0
 */
@RestController
public class TestController {

    @Autowired
    Service service;
    @Autowired
    Config config;

    @RequestMapping("/test")
    public String f1(){

       service.f2();

       return "f2";

    }


    @RequestMapping("/test1")
    public String f12(){

        return config.f1().toString();

    }

    @RequestMapping("/test2")
    public String f123(){

        return config.f3().toString();

    }

    @RequestMapping("/test3")
    public void f123(P1 p1){

        System.out.println(p1);


    }
}
