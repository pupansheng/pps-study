/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com.pps.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.pps.fegin.InvokeRemoteService;
import com.pps.fegin.InvokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pu PanSheng, 2021/12/31
 * @version OPRA v1.0
 */
@RestController
public class TestController {

    @Autowired
    private InvokeService invokeService;
    @Autowired
    private InvokeRemoteService invokeRemoteService;
    @Autowired
    private Environment environment;

    @RequestMapping("/test")
    public Map test(Integer time){

        if(time==null){
            time=9000;
        }
        try {
            System.out.println("time"+time);
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        HashMap map=new HashMap();
        map.put("info","dd");

        return map;

    }

    @RequestMapping("/test1")
    public Map test2(Integer time,Integer TYPE){

        if(time==null){
            time=90;
        }

        System.out.println(new Date()+"start  time"+time);
        Map test = null;
        try {
            if(TYPE==null) {
                test = invokeService.test(time);
            }else {
                test=invokeRemoteService.test(time);
            }
        } catch (Exception e) {

            System.out.println(new Date()+"end");
            System.out.println("result"+test);
            e.printStackTrace();
        }

        System.out.println("result2"+test);
        return test;

    }


    @RequestMapping("/test2")
    public void test3(Integer time){

        System.out.println(environment);

    }



    @HystrixCommand()
    public void f(){



    }
}
