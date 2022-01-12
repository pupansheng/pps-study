/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com.pps.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Pu PanSheng, 2021/12/31
 * @version OPRA v1.0
 */
@FeignClient(name = "ppstest",url = "localhost:8080"/*,fallback = InvokeService.InvokeServiceCallback.class*/)
public interface InvokeService {

    @GetMapping("/test")
    Map test(@RequestParam("time") Integer time);

    @Component
    public class InvokeServiceCallback implements InvokeService{

        @Override
        public Map test(Integer time) {

            HashMap map=new HashMap();
            map.put("info","error");


            return map;
        }
    }
}
