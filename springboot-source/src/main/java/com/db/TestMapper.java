/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com.db;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Pu PanSheng, 2022/1/11
 * @version OPRA v1.0
 */
public interface TestMapper {


    @Select("select * from TEST_USER")
    List<User> queryList();

    @Select("select * from TEST_USER where id=#{id}")
    User queryOne(Integer id);
    @Update("update TEST_USER set name=#{name} where id=#{id}")
    int update(Integer id,String name);

}
