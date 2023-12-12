/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package warp;

/**
 * @author Pu PanSheng, 2021/12/14
 * @version OPRA v1.0
 */
public class StaticNode {


    private QpsStatic qpsStatic;

    private String key;

    private long timeLong;

    private long windowLong;

    public String getKey() {
        return key;
    }

    public StaticNode(long timeLong, int windowSize, String key){
        long timePer=timeLong/windowSize;
        this.windowLong=timePer;
        this.timeLong=timeLong;
        qpsStatic=new QpsStatic(timeLong, timePer);
        this.key=key;
    }
    public void addPass(){
        qpsStatic.click(0);
    }
    public void addError(){
        qpsStatic.click(1);
    }
    public int qps(long timeCur, int type){
        int i1 = qpsStatic.staticQps(timeCur,type);
        return i1;
    }

    public long getTimeLong() {
        return timeLong;
    }

    public long getWindowLong() {
        return windowLong;
    }
}
