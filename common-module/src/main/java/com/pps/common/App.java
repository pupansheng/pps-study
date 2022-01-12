package com.pps.common;/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */

/**
 * @author Pu PanSheng, 2021/12/28
 * @version OPRA v1.0
 */
public class App {
    public static void main(String args[]){


        Person person=new Person();
        System.out.println(person.getClass().getClassLoader());
        System.out.println(App.class.getClassLoader());
        person.say();
    }
}
