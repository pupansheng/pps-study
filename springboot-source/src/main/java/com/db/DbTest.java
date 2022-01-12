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

import javax.sql.DataSource;
import java.util.List;

/**
 * @author Pu PanSheng, 2022/1/11
 * @version OPRA v1.0
 */
public class DbTest {

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


        List<User> users = sqlSession.getMapper(TestMapper.class).queryList();

        System.out.println(users);


    }


}
