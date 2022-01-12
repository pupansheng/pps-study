package mvc;/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */

import com.pps.boot.PpsBootApp;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Pu PanSheng, 2021/12/29
 * @version OPRA v1.0
 */
@ComponentScan(value = "mvc.test")
public class Test {


    public static void main(String args[]){


        PpsBootApp.run(Test.class);



    }


}
