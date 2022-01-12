/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package 泛型测试;

/**
 * @author Pu PanSheng, 2022/1/11
 * @version OPRA v1.0
 */
public interface TestInterface {

    <T> T queryOne();

    <T> T selectOne(String statement);
}
