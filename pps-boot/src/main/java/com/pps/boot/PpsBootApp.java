package com.pps.boot;/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */


import com.pps.boot.context.PpsWebApplicationContext;
import com.pps.boot.listener.PpsBootRunListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

import java.util.ServiceLoader;

/**
 * @author Pu PanSheng, 2021/12/29
 * @version OPRA v1.0
 */
public class PpsBootApp {


    private Class[] premarySource;

    public PpsBootApp(Class[] premarySource) {
        this.premarySource = premarySource;
    }


    public void run(){

        ServiceLoader<PpsBootRunListener> load = ServiceLoader.load(PpsBootRunListener.class);


        //准备环境
        ConfigurableEnvironment environment = createEnvironment();

        load.forEach(s->s.preEnv(environment));

        PpsWebApplicationContext ppsWebApplicationContext=new PpsWebApplicationContext();
        ppsWebApplicationContext.setEnvironment(environment);


        load.forEach(s->s.contextPre(ppsWebApplicationContext));
        ppsWebApplicationContext.register(premarySource);




        ppsWebApplicationContext.refresh();
        load.forEach(s->s.freshAfter(ppsWebApplicationContext));



    }
    public ConfigurableEnvironment createEnvironment() {

        StandardEnvironment standardEnvironment = new StandardEnvironment();

        return  standardEnvironment;
    }

    public static void run(Class ...classes){

        PpsBootApp ppsBootApp = new PpsBootApp(classes);
        ppsBootApp.run();

    }




}
