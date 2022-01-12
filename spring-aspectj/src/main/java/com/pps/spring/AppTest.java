/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com.pps.spring;

import com.pps.common.Person;

/**
 * @author Pu PanSheng, 2021/12/28
 * @version OPRA v1.0
 */
public class AppTest {



    public static void main(String args[]){

        Person person=new Person();
        person.say();
        System.out.println(person.getClass().getClassLoader());


    }

}
