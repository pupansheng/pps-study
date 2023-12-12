import http.HttpClient;
import http.HttpRequest;
import http.HttpResponse;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Pu PanSheng, 2022/1/17
 * @version v1.0
 */
public class Test2 {


    public static void main(String args[]) throws InterruptedException {


        HttpClient httpClient=new HttpClient(1);
        AtomicInteger atomicInteger=new AtomicInteger();
        AtomicInteger atomicIntegerR=new AtomicInteger();

            System.out.println(new Date() + " ：发起请求：" + atomicInteger.addAndGet(1));
            //Thread.sleep(50);

            HttpRequest httpRequest = new HttpRequest();
            httpRequest.setMethod("GET");
            httpRequest.setUrl("http://127.0.0.1:8090/qpstest");
            httpRequest.addParam("name", "pps");
            Map map = new HashMap();
            map.put("key", "test");
            httpRequest.setBody(map);
            httpRequest.setContentType("application/json");
            httpClient.executeHttp(httpRequest, (HttpResponse<Map> r) -> {
                Object body = r.getBody();
                System.out.println(body.getClass());
                System.out.println("收到响应："+body);
            });





    }



}
