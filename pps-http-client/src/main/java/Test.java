import core.DataClient;
import core.Request;
import http.HttpClient;
import http.HttpRequest;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import util.BufferUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Pu PanSheng, 2022/1/17
 * @version v1.0
 */
public class Test {


    public static void main(String args[]) throws InterruptedException {


        HttpClient httpClient=new HttpClient(1);
        AtomicInteger atomicInteger=new AtomicInteger();
        AtomicInteger atomicIntegerR=new AtomicInteger();

        Runnable runnable=()->{

            for (int i2 = 0; i2 < 1; i2++) {


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
            int i = atomicInteger.addAndGet(1);
            if(i>100){
                break;
            }
            System.out.println(new Date() + " ：发起请求：" + i);
            httpClient.executeHttp(httpRequest, r -> {
                 System.out.println("收到响应："+atomicIntegerR.addAndGet(1));
            });


        }



        };

        new Thread(runnable).start();

    }



}
