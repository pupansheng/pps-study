/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com.pps.loadweave;

import com.pps.common.Person;

/**
 * 类加载时织入
 * vm 参数
 * -javaagent:spring-aspectj/bin/aspectjweaver.jar
 * @author Pu PanSheng, 2021/12/28
 * @version OPRA v1.0
 */
public class Test {


    public static void main(String args[]){

        Person person=new Person();

        person.say();


    }

}
