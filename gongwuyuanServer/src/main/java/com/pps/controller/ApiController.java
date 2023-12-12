package com.pps.controller;

import com.pps.es.ReportInfo;
import com.pps.service.EsService;
import com.pps.vo.ReportReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: pps
 * @Date: 2023/12/12 16:56
 * @Description:
 **/
@RestController
public class ApiController {
    @Autowired
    private EsService esService;
    @GetMapping("/report/page")
    Page<ReportInfo> reportInfoPages(ReportReq req) {
        return esService.reportInfo(req);
    }
}
