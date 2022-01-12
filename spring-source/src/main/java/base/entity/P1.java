/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package base.entity;

import base.advice.aoptest.MyTraction;
import org.springframework.stereotype.Component;

/**
 * @author Pu PanSheng, 2021/12/23
 * @version OPRA v1.0
 */
@Component
public class P1 {

    public P1(){

        System.out.println("p1");
    }

    @MyTraction
    public void f1(){


        System.out.println("f111111111111111111111111111");

    }


}
