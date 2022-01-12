/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package mvc.test;

import mvc.annotion.PpsController;
import mvc.annotion.PpsRequestMapping;

import java.util.Map;

/**
 * @author Pu PanSheng, 2021/12/29
 * @version OPRA v1.0
 */
@PpsController
public class PpsTestController {

    @PpsRequestMapping(url = "/test")
    public String s(Map map){


        return "test"+map.toString();
    }


}
