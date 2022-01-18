/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com.controller;

/**
 * @author Pu PanSheng, 2021/12/14
 * @version OPRA v1.0
 */
public class Entity {

    private String key;

    private Integer count;

    private Integer error;

    private long perid;

    private String time;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public long getPerid() {
        return perid;
    }

    public void setPerid(long perid) {
        this.perid = perid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
