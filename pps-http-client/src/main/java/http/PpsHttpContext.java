package http;

import core.Context;
import core.Response;
import util.BufferUtil;
import util.PpsInputSteram;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Pu PanSheng, 2022/1/17
 * @version v1.0
 */

public class PpsHttpContext extends Context {


    public PpsHttpContext(SocketChannel socketChannel, Consumer<Response> consumerumer) {
        super(socketChannel, consumerumer);

    }

    @Override
    public void endCallBack(Response response) {

     consumer.accept(response);

    }


    @Override
    public void read() throws IOException {

        //http报文 请求行和请求头
        List<String> httpReportHead = new ArrayList<>();
        byte[] line = new byte[1024];
        int index = 0;
        while (true) {

            int data = (byte) ppsInputSteram.read();

            if (data > -1000) {

                line[index] = (byte) data;

                if (data == '\n' && index != 0 && line[index - 1] == '\r') {

                    String s = new String(line, 0, index + 1, "utf-8");
                    //如果s==\r\n 那么说明这个就是请求体和请求头的那个分隔标记 下面的字节就是请求体了
                    if (s.equals("\r\n")) {
                        break;
                    }
                    httpReportHead.add(s);
                    for (int i = 0; i < line.length; i++) {
                        line[i] = 0;
                    }
                    index = 0;
                    continue;
                }

                index++;
                //扩容
                if (index >= line.length) {
                    byte[] newLine = new byte[line.length * 2];
                    System.arraycopy(line, 0, newLine, 0, index);
                    line = newLine;
                }

            } else {
                break;
            }
        }


        Response response=new Response(ppsInputSteram);
        response.getContentMap().put("httpHead", httpReportHead);
        endCallBack(response);

    }






}


