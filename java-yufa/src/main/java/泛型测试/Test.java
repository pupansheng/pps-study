/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package 泛型测试;

/**
 * @author Pu PanSheng, 2022/1/11
 * @version OPRA v1.0
 */
public class Test implements TestInterface{





    public static void main(String args[]){

       TestInterface testInterface=null;

        Object o = testInterface.queryOne();

    }

    @Override
    public <T> T queryOne() {
        return (T)"s";
    }

    @Override
    public <T> T selectOne(String statement) {
        return null;
    }
}
