package base;/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author Pu PanSheng, 2021/8/9
 * @version OPRA v1.0
 */
@Component
public class MyListener implements ApplicationListener {
    public void onApplicationEvent(ApplicationEvent event) {

        System.out.println("event---:"+event);

    }
}
