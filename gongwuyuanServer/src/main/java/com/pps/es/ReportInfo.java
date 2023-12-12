package com.pps.es;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author: pps
 * @Date: 2023/12/12 16:34
 * @Description:
 **/
@Data
@Accessors(chain = true)
public class ReportInfo {

    private String title;

    private String area;

    private String type;

    private String detail;

    private Integer zhaopinCount;

    private Date publishTime;

    private Date addTime;

}
