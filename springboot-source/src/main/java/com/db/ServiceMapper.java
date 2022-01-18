/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com.db;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Pu PanSheng, 2022/1/11
 * @version OPRA v1.0
 */
@Service
@Transactional
public class ServiceMapper {

    @Autowired
    TestMapper testMapper;


    public List<User> queryList(){
        return testMapper.queryList();
    }


    public User queryOne(Integer id){
        User user = testMapper.queryOne(id);
        System.out.println("1:"+   user);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        User user2 = testMapper.queryOne(id);
        System.out.println("2:"+     user2);
        return user2;
    }
    public User queryOne1(Integer id){
        User user = testMapper.queryOne(id);
        return user;
    }


    public int update(Integer id, String name){
        return testMapper.update(id, name);
    }
}
