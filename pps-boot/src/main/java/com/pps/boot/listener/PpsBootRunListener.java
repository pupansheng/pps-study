/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com.pps.boot.listener;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author Pu PanSheng, 2021/12/29
 * @version OPRA v1.0
 */
public interface PpsBootRunListener {


    void preEnv(ConfigurableEnvironment environment);



    void contextPre(ConfigurableApplicationContext context);



    void freshAfter(ConfigurableApplicationContext context);



}
