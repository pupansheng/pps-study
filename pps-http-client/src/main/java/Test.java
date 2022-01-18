import core.DataClient;
import core.Request;
import http.HttpClient;
import http.HttpRequest;
import util.BufferUtil;

/**
 * @author Pu PanSheng, 2022/1/17
 * @version v1.0
 */
public class Test {


    public static void main(String args[]) throws InterruptedException {


        HttpClient httpClient=new HttpClient(1);

        HttpRequest httpRequest=new HttpRequest();
        httpRequest.setMethod("GET");
        httpRequest.setUrl("http://127.0.0.1:8090/qpstest");
        httpClient.executeHttp(httpRequest, r -> {
            System.out.println(r);

        });





    }



}
