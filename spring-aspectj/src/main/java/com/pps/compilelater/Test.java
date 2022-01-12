/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com.pps.compilelater;

import com.pps.common.Person;
import com.pps.compiletime.Service;

/**
 * 编译后织入
 * @author Pu PanSheng, 2021/12/28
 * @version OPRA v1.0
 */
public class Test {

    public static void main(String args[]){


        Person person=new Person();
        person.say();

        Service service=new Service();
        service.doThing("123");

    }

}
