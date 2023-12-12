/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */

import org.junit.Test;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Pu PanSheng, 2022/1/12
 * @version OPRA v1.0
 */
public class JavaTest {


    @Test
    public void f1(){


        String s="sdsd";
        System.out.println(s.substring(0,255));

    }

    @Test
    public void f2(){

        Optional.ofNullable(null).orElseThrow(()->new RuntimeException());

    }

    @Test
    public void f3(){

        String url="http://git.acca.com.cn/projects/OPRA-GIT/repos/opra-config-repo/browse/opra/mch-rpt/application.yml";
        int i = url.indexOf("://");
        String substring = url.substring(i+3);
        int i1 = substring.indexOf("/");
        String substring1 = substring.substring(0, i1);
        int index=  substring1.indexOf(":");
        String port;
        String ip;
        if(index!=-1){
            port= substring1.substring(index + 1);
             ip=substring1.substring(0, index);
        }else {

            ip=substring1;
             port="80";

        }

        System.out.println(ip);
        System.out.println(port);


    }
    
    
    @Test
    public void f4(){
        
        String emdRelatedTicketNo="9991234567890";

        String  prefix= emdRelatedTicketNo.substring(0, 3);
        String tktNo=emdRelatedTicketNo.substring(3);
        System.out.println("no:"+tktNo+"prefix:"+prefix);


    }

}
