/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package core;

import util.PpsInputSteram;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Pu PanSheng, 2022/1/18
 * @version OPRA v1.0
 */
public abstract class Context {

    protected SocketChannel socketChannel;

    protected PpsInputSteram ppsInputSteram;

    protected Consumer<Response> consumer;

    private List<byte[]> datas=new ArrayList<>(2);

    private List<byte[]> nextData=new ArrayList<>(0);

    public Context(SocketChannel socketChannel, Consumer<Response> consumer) {
        this.socketChannel = socketChannel;
        this.consumer = consumer;
    }



    public void addData(byte[] bytes){
        datas.add(bytes);
    }
    public void addNextData(byte[] bytes){
        nextData.add(bytes);
    }

    public abstract boolean isEnd(byte [] endLine);


    public void endCallback(){

        //调用
        callBack();

        //善后
        datas.clear();
        datas.addAll(nextData);
        nextData.clear();
    }

    public abstract void callBack();

    public PpsInputSteram getPpsInputSteram() {
        return ppsInputSteram;
    }

    public void setPpsInputSteram(PpsInputSteram ppsInputSteram) {
        this.ppsInputSteram = ppsInputSteram;
    }

    public Consumer<Response> getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer<Response> consumer) {
        this.consumer = consumer;
    }

    public List<byte[]> getDatas() {
        return datas;
    }

    public void setDatas(List<byte[]> datas) {
        this.datas = datas;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }
}
