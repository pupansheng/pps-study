/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * @author Pu PanSheng, 2022/1/11
 * @version OPRA v1.0
 */
public class
DbTest {

    public static void main(String args[]){


        HikariConfig hikariConfig=new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("pps123");
        hikariConfig.setJdbcUrl("jdbc:mysql://110.42.191.254:3306/test?useUnicode=true&characterEncoding=utf8");

        DataSource dataSource=new HikariDataSource(hikariConfig);


        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(TestMapper.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);


        SqlSession sqlSession = sqlSessionFactory.openSession();
        TestMapper mapper = sqlSession.getMapper(TestMapper.class);

        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        TestMapper mapper2 = sqlSession2.getMapper(TestMapper.class);

        User user = mapper.queryOne(1);
        User user1 = mapper2.queryOne(1);
        mapper.update(1,"gengxin"+ ThreadLocalRandom.current().nextInt(10));
        sqlSession.commit();
        System.out.println(" 1  user: "+user);
        System.out.println(" 1  user1: "+user1);

        User user2 = mapper.queryOne(1);
        User user12 = mapper2.queryOne(1);
        System.out.println("2  user: "+user2);
        System.out.println("2  user1: "+user12);





    }




    public void f11(String s1,String aa) throws NoSuchMethodException {


        Arrays.stream(DbTest.class.getDeclaredMethod("f11", String.class,String.class).getParameters())
                .map(Parameter::getName).collect(Collectors.toList()).forEach(System.out::println);
    }

    @Test
    public void f112() throws NoSuchMethodException {

        f11("1","2");
    }
}
