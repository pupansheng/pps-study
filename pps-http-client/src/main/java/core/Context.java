/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package core;

import util.PpsInputSteram;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import java.util.function.Consumer;

/**
 * @author Pu PanSheng, 2022/1/18
 * @version OPRA v1.0
 */
public abstract class Context {

    protected SocketChannel socketChannel;

    protected PpsInputSteram ppsInputSteram;

    protected Consumer<Response> consumer;


    public Context(SocketChannel socketChannel, Consumer<Response> consumer) {
        this.socketChannel = socketChannel;
        this.consumer = consumer;
    }

    public abstract void endCallBack(Response response);

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

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    public abstract void read() throws IOException;
}
