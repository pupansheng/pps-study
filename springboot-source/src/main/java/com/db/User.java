/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com.db;

import java.util.Date;

/**
 * @author Pu PanSheng, 2021/9/1
 * @version OPRA v1.0
 */
public class User {

    private int id;

    private String name;

    private Date birthday;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setNames(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public static void main(String args[]){

        User user=new User();
        user.getId();


    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
