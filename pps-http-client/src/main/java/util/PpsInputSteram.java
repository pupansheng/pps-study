/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

/**
 * @author Pu PanSheng, 2021/12/18
 * @version OPRA v1.0
 */
public class PpsInputSteram extends InputStream {

    private int readIndex;
    private int nowCap;
    private SocketChannel socketChannel;
    private SelectionKey selectionKey;
    private ByteBuffer byteBuffer;

    public PpsInputSteram(SocketChannel socketChannel, SelectionKey selectionKey) {
        this.socketChannel = socketChannel;
        this.selectionKey=selectionKey;
        byteBuffer=BufferUtil.getBuffer();
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public SelectionKey getSelectionKey() {
        return selectionKey;
    }

    public void returnBuffer(){
        BufferUtil.returnBuffer(byteBuffer);
    }

    @Override
    public int read() throws IOException {

        if(readIndex>=nowCap){
            byteBuffer.clear();
            int read=socketChannel.read(byteBuffer);
            nowCap=read;
            if(read>0){
                nowCap=read;
                readIndex=0;
                byteBuffer.flip();
            }else if(read<0){
                //通道已关闭
                //待议
                selectionKey.cancel();
                socketChannel.close();
                BufferUtil.returnBuffer(byteBuffer);
                return -1001;
            }else {
                BufferUtil.returnBuffer(byteBuffer);
                return -1000;
            }

        }
        byte b = byteBuffer.get(readIndex++);

        return b;

    }

    @Override
    public int available() throws IOException {

        throw new UnsupportedOperationException("不支持");
    }

    public void cancel(){

        selectionKey.cancel();
        try {
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
