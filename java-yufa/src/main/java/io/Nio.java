package io;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author Pu PanSheng, 2022/1/16
 * @version v1.0
 */
public class Nio {

    /**
     * 同步非阻塞io
     * 当要使用同步操作时  如果没有数据 立马返回 不会阻塞在哪里
     * 比如 read()
     * 没有数据 那么立马返回  0
     * 有数据 >0
     * 通道关闭 -1
     * @throws Exception
     */
    @Test
    public void f1() throws Exception {


            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open() ;
            serverSocketChannel.bind(new InetSocketAddress(8080));
            serverSocketChannel.configureBlocking(true);

          SocketChannel accept = serverSocketChannel.accept();
        if(accept!=null) {
            SocketAddress remoteAddress = accept.getRemoteAddress();
            System.out.println("收到连接："+remoteAddress);
        }

        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);

        accept.configureBlocking(false);
        while (true){

            Thread.sleep(100);
            int read = accept.read(byteBuffer);
            System.out.println("收到信息size："+read);

        }


    }
}
