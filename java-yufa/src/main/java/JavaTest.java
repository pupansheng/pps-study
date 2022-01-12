/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */

import org.junit.Test;

/**
 * @author Pu PanSheng, 2022/1/12
 * @version OPRA v1.0
 */
public class JavaTest {


    @Test
    public void f1(){


        String s="";

        if(s.startsWith("\"")){
            s=s.substring(1);
        }

        if(s.endsWith("\"")){
            s=s.substring(0,s.length()-1);
        }

        System.out.println(s);


    }

}
