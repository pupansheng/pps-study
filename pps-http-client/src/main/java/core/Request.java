package core;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Pu PanSheng, 2022/1/17
 * @version v1.0
 */
public class Request {


    private String ip;

    private Integer port=80;

    private byte[] body=new byte[0];

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
