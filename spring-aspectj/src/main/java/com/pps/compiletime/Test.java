/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com.pps.compiletime;

/**
 * 编译期织入理解起来应该还是比较简单，
 * 就是在编译的时候先修改了代码再进行编译。
 * @author Pu PanSheng, 2021/12/28
 * @version OPRA v1.0
 */
public class Test {

    public static void main(String[] args) {

        Service service=new Service();
        service.doThing("123");

    }




}
