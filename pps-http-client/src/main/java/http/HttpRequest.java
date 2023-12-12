
package http;

import com.alibaba.fastjson.JSON;
import util.BufferUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Pu PanSheng, 2021/12/18
 * @version OPRA v1.0
 */
public class HttpRequest {

    private String protocol="HTTP/1.1";
    private String method="GET";
    private String url;
    private String ip;
    private int port;
    private String contentType="application/json";
    private byte [] body=new byte[0];
    private Map<String, String> requestPrams=new HashMap<>();
    private String charset="utf-8";
    private Map<String,String> headerParam=new HashMap<>();

    {
        headerParam.put("Accept","*/*");
        headerParam.put("Connection","Keep-Alive");
        //headerParam.put("Accept-Encoding","gzip");
        headerParam.put("Accept-Language","zh-cn");
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String hostname = addr.getHostAddress();
        //必须要这个
        headerParam.put("Host", hostname);
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {

        return url;

    }

    public void setUrl(String url) {


        if(!url.endsWith("/")){
            url=url+"/";
        }
        int i = url.indexOf("://");
        String substring = url.substring(i+3);
        int i1 = substring.indexOf("/");
        String substring1 = substring.substring(0, i1);
        int index=  substring1.indexOf(":");
        String port;
        String ip;
        if(index!=-1){//有端口
            port= substring1.substring(index + 1);
            ip=substring1.substring(0, index);
        }else {//没有端口
            ip=substring1;
            port="80";
        }

        this.ip=ip;
        this.port=Integer.parseInt(port);
        this.url=substring.substring(i1);



    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void addHeader(String name, String v){
        headerParam.put(name,v);
    }

    public void setBody(Object o){
        String s = JSON.toJSONString(o);
        body=BufferUtil.strToBytes(s, charset);
    }
    public void setBody(byte[] o){
        body=o;
    }


    public void addParam(String k,String v){

        requestPrams.put(k,v);

    }




    void  check(){

        Optional.ofNullable(url).orElseThrow(()->new RuntimeException("url 不能为空"));

    }

    byte[] createHttpMessage(){

         if(method.equals("POST")||method.equals("PUT")){

             if(contentType.equals("application/x-www-form-urlencoded")){
                 StringBuilder stringBuilder=new StringBuilder();
                 requestPrams.forEach((k,v)->{
                     stringBuilder.append(k+"="+v);
                     stringBuilder.append("&");
                 });
                 String s = stringBuilder.toString();
                 String substring = s.substring(0, s.length() - 1);
                 body=BufferUtil.strToBytes(substring);
             }

         }else if(method.equals("GET")){



         }else if(method.equals("HEAD")){


         }else if(method.equals("DELETE")){


         }

        check();

        return createMessage(body);

    }

    /**
     * 构造 请求头 带有content_length
     * @param httpr
     * @return
     */
    private byte[] createMessage(String httpr) {
        return createMessage(BufferUtil.strToBytes(httpr,charset));
    }

    /**
     * 构造 普通的氢气头 带有content_length
     * @param httpr
     * @return
     */
    private byte[] createMessage(byte [] httpr)  {

        StringBuilder returnStr = new StringBuilder();
        //请求行
        appendResponLine(returnStr,String.format("%s %s %s"
                ,method.toUpperCase()
                ,url,
                protocol));//增加响应消息行

        //请求头
        compreHander(returnStr,true);

        /**
         * 可能会被压缩
         */
        httpr=encoding(httpr,0,httpr.length);


        String contentLen=String.valueOf(httpr.length);

        appendResponseHeader(returnStr,"Content-Type: "+contentType+";charset=" + charset);
        appendResponseHeader(returnStr,String.format("Content-Length: %s",contentLen));
        headerParam.forEach((k,v)->{
            appendResponseHeader(returnStr,k +": "+v);
        });


        returnStr.append("\r\n");

        //请求体
        String s= returnStr.toString();
        byte[] bytes = BufferUtil.strToBytes(s,charset);

        int i = httpr.length + bytes.length;
        byte[] newC=new byte[i];
        System.arraycopy(bytes,0,newC,0,bytes.length);
        System.arraycopy(httpr,0,newC,bytes.length,httpr.length);
        return newC;
    }


    /**
     * 压缩头添加
     * @param stringBuilder
     */
    private void compreHander(StringBuilder stringBuilder, boolean force){


    }

    byte [] encoding(byte [] bytes, int offset, int len){
        return bytes;
    }
    /**
     * 构造http 分块请求头 不带有content_length
     *
     * 压缩算法例如gizp 和 chunck分块传输 如何组合呢
     *
     * 答：
     * 只能先要把发送的数据 用gzip 组合起来 然后再分块传输
     * 而不是 对每一块分别进行压缩后 再发送
     * @return
     */
    private byte[] createMessageChunckHead()  {

        StringBuilder returnStr = new StringBuilder();
        //请求行
        appendResponLine(returnStr,String.format("%s %s s%"
                ,method
                ,url
                ,protocol));//增加响应消息行
        //请求头
        appendResponseHeader(returnStr,"Content-Type: "+contentType+";charset=" + charset);
        appendResponseHeader(returnStr,String.format("Transfer-Encoding: %s","chunked"));

        headerParam.forEach((k,v)->{
            appendResponseHeader(returnStr,k +": "+v);
        });

        returnStr.append("\r\n");
        byte[] bytes =null;
        bytes = BufferUtil.strToBytes(returnStr.toString(),charset);
        return bytes;

    }

    private byte [] createChunckBody(byte [] bytes,int offset,int lenA){

        String len = Integer.toHexString(lenA);
        String h=len+"\r\n";
        byte[] bytes1 = BufferUtil.strToBytes(h);
        int i = lenA + bytes1.length;
        byte[] n=new byte[i+2];
        System.arraycopy(bytes1,0,n,0,bytes1.length);
        System.arraycopy(bytes,offset, n,bytes1.length,lenA);
        n[i]='\r';
        n[i+1]='\n';
        return n;

    }
    private byte [] createEndChunckBody(){

        String end="0\r\n\r\n";
        byte[] bytes = BufferUtil.strToBytes(end);
        return bytes;

    }
    private void appendResponLine(StringBuilder stringBuilder,String line){
        stringBuilder.append(line+"\r\n");
    }
    private void appendResponseHeader(StringBuilder stringBuilder,String line){
        stringBuilder.append(line+"\r\n");
    }


}
