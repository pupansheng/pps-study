import util.BufferUtil;

/**
 * @author Pu PanSheng, 2022/1/17
 * @version v1.0
 */
public class Test {


    public static void main(String args[]){


        HttpClient httpClient=new HttpClient(1);
        BufferUtil.initBufferPool();

        Request request=new Request();

        request.setIp("127.0.0.1");
        request.setPort(9090);
        String s="GET /qps HTTP/1.1  \n" +
                "Accept: image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, application/x-silverlight, application/x-shockwave-flash, */*  \n" +
                "Referer: <a href=\"http://www.google.cn/\">http://www.google.cn/</a>  \n" +
                "Accept-Language: zh-cn  \n" +
                "Accept-Encoding: gzip, deflate  \n" +
                "User-Agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 2.0.50727; TheWorld)  \n" +
                "Host: <a href=\"http://www.google.cn\">www.google.cn</a>  \n" +
                "Connection: Keep-Alive  \n" +
                "Cookie: PREF=ID=80a06da87be9ae3c:U=f7167333e2c3b714:NW=1:TM=1261551909:LM=1261551917:S=ybYcq2wpfefs4V9g; NID=31=ojj8d-IygaEtSxLgaJmqSjVhCspkviJrB6omjamNrSm8lZhKy_yMfO2M4QMRKcH1g0iQv9u-2hfBW7bUFwVh7pGaRUb0RnHcJU37y-FxlRugatx63JLv7CWMD6UB_O_r";
        request.setBody(s.getBytes());



            httpClient.execute(request, response -> {
                byte[] content = response.getContent();
                System.out.println("threadId:" + Thread.currentThread().getId());
                System.out.println(new String(content));
                System.out.println("----------------------------------------------");

            });






    }



}
