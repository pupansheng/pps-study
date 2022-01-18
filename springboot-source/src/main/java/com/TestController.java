/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com;

import com.config.Config;
import com.config.Service;
import com.db.ServiceMapper;
import com.db.TestMapper;
import com.db.User;
import com.entity.P1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warp.StaticNode;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    @Autowired
    TestMapper testMapper;
    @Autowired
    ServiceMapper serviceMapper;









}
