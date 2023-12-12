/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package warp;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Pu PanSheng, 2021/7/28
 * @version OPRA v1.0
 */
public class Node {


    /**
     * 0  pass
     * 1  error
     */
    private AtomicInteger[] countTyps={new AtomicInteger(0),new AtomicInteger(0)};
    private long start;

    public AtomicInteger[] getCountTyps(){
        return  countTyps;
    }


    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }
}
