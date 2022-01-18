
package http;


import java.util.*;

/**
 * @author Pu PanSheng, 2021/12/18
 * @version OPRA v1.0
 */
public class HttpResponse {

    private String protocol;
    private String code;
    private Map<String,String> headerParams=new HashMap<>();
    private byte [] body=new byte[0];


    public HttpResponse(byte [] content){


        int length = content.length;
        List<String> header=new ArrayList<>();
        int start=0;
        for (int i = 0; i < length; i++) {

            if(content[i]=='\n'&&content[i-1]=='\r'&&i!=0){

                int len=i-start+1;

                if(len==2){//请求行
                    start=i+1;
                    break;
                }
                header.add(new String(content,start, len));
                start=i+1;
            }

        }

        resolveHttpHead(header);


        if(start<length) {
            int bL = length - start;
            byte[] bytes = new byte[bL];
            for (int i = start; i < length; i++) {
                bytes[i - start] = content[i];
            }
            body = bytes;
        }

    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, String> getHeaderParams() {
        return headerParams;
    }

    public void setHeaderParams(Map<String, String> headerParams) {
        this.headerParams = headerParams;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    private void resolveHttpHead(List<String> httpReportHead){

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
                ", body=" + Arrays.toString(body) +
                '}';
    }
}
