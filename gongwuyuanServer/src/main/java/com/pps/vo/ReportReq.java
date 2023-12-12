package com.pps.vo;

import lombok.Data;

/**
 * @Author: pps
 * @Date: 2023/12/12 18:29
 * @Description:
 **/

@Data
public class ReportReq {

    private Integer size=10;

    private Integer page=0;

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 区域
     */
    private String area;
    /**
     * 类型 事业单位
     */
    private String type;
}
