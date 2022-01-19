
package http;


import com.alibaba.fastjson.JSON;
import core.Response;
import core.exception.ChannelCloseException;
import util.BufferUtil;
import util.PpsInputSteram;

import javax.print.DocFlavor;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author Pu PanSheng, 2021/12/18
 * @version OPRA v1.0
 */
public class HttpResponse<T> {

    private String protocol;
    private String code;
    private Map<String,String> headerParams=new HashMap<>();
    private T body;

    public String getContentLength(){
        return  Optional.ofNullable(headerParams.get("content-length")).orElse(headerParams.get("Content-Length"));
    }
    public String getContentType(){
        return  Optional.ofNullable(headerParams.get("content-Type")).orElse(headerParams.get("Content-Type"));
    }
    public String getTransferEncoding(){
        return  Optional.ofNullable(headerParams.get("Transfer-Encoding")).orElse(headerParams.get("Transfer-Encoding"));
    }

    public HttpResponse(Response response)  {

         //INIT
        List<String> list = (List<String>) response.getContentMap().get("httpHead");
        resolveHttpHead(list);

        String transferEncoding = getTransferEncoding();
        PpsInputSteram ppsInputSteram = response.getPpsInputSteram();
        List<byte[]> bodyByte=new ArrayList<>();
        if(transferEncoding!=null){

            byte[] line = new byte[1024];
            int index = 0;
            while (true) {

                //读第一行
                while (true) {
                    int data = 0;
                    try {
                        data = (byte) ppsInputSteram.read();
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new ChannelCloseException(e);
                    }

                    if (data > -1000) {
                        line[index] = (byte) data;
                        if (data == '\n' && index != 0 && line[index - 1] == '\r') {
                            index++;
                            break;
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

                if(index==0){
                    break;
                }

                //读第二行
                // cc\r\n
                String ss=new String(line, 0, index);


                byte[] byteArray = BufferUtil.getByteArray(line, 0, index - 2);
                String s = new String(byteArray);
                int b = Integer.parseInt(s.replaceAll("^0[x|X]", ""), 16);


                byte[] content=new byte[b+2];
                try {
                    ppsInputSteram.read(content);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new ChannelCloseException(e);
                }

                if(b==0){
                    break;
                }

                bodyByte.add(BufferUtil.getByteArray(content, 0, b));

                index=0;
            }


        }else {
            try {
                bodyByte.add(ppsInputSteram.readAll());
            } catch (IOException e) {
                e.printStackTrace();
                throw new ChannelCloseException(e);
            }
        }

        //压缩处理

        Class<?> aClass = getType();
        String contentType = getContentType();
        byte[] bytes = BufferUtil.listToArray(bodyByte);
        if(aClass==String.class){
            body=(T)new String(bytes);
        }else if(contentType==null){
            body= (T) bytes;
        } else if(contentType.toUpperCase().equals(ContentTypeEnum.applicationjson.getType().toUpperCase())){
            body=JSON.parseObject(bytes, aClass);
        }else  if(contentType.toUpperCase().equals(ContentTypeEnum.texthtml.getType().toUpperCase())){
            body=(T)new String(bytes);
        }else {
            System.out.println("WARN: "+contentType+"未配置解析方法 ");
            body= (T) bytes;
        }

    }

    Class getType(){

        Class clazz=this.getClass();
        Type genType = clazz.getGenericSuperclass();

        for(Class superClass = clazz; !(genType instanceof ParameterizedType); genType = superClass.getGenericSuperclass()) {
            if (superClass == Object.class) {
                return Object.class;
            }
            superClass = superClass.getSuperclass();
        }
        Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
        if(params.length>0){
            return params[0].getClass();
        }else {
            return Object.class;
        }


    }
    void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    void setCode(String code) {
        this.code = code;
    }

    void setHeaderParams(Map<String, String> headerParams) {
        this.headerParams = headerParams;
    }

    void setBody(T body) {
        this.body = body;
    }

    public T getBody() {
        return body;
    }

    public String getProtocol() {
        return protocol;
    }



    public String getCode() {
        return code;
    }



    public Map<String, String> getHeaderParams() {
        return headerParams;
    }



    void resolveHttpHead(List<String> httpReportHead){

        //解析请求头
        if(!httpReportHead.isEmpty()) {
            //请求行
            String requestLine= httpReportHead.get(0);
            String[] lineArr = requestLine.split(" ");
            protocol=lineArr[0];
            code=lineArr[1];
            //解析请求头
            for (int i = 1; i < httpReportHead.size(); i++) {
                String[] split1 = httpReportHead.get(i).split(":");
                if(split1.length==2){
                    headerParams.put(split1[0], split1[1].trim());
                }
            }

        }


    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "protocol='" + protocol + '\'' +
                ", code='" + code + '\'' +
                ", headerParams=" + headerParams +
                ", body=" + body +
                '}';
    }
}
