package core;

import util.BufferUtil;

import java.nio.channels.SocketChannel;
import java.util.function.Consumer;

/**
 * @author Pu PanSheng, 2022/1/17
 * @version v1.0
 */

public class PpsContext extends Context {


    public PpsContext(SocketChannel socketChannel, Consumer<Response> consumerumer) {
        super(socketChannel, consumerumer);

    }

    @Override
    public boolean isEnd(byte[] endLine) {
        return true;
    }

    @Override
    public void callBack() {
        Response response=new Response();
        response.setContent(BufferUtil.listToArray(getDatas()));
        consumer.accept(response);
    }


}
