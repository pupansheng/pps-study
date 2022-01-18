/*
 * Copyright (c) ACCA Corp.
 * All Rights Reserved.
 */
package com.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warp.StaticNode;
import warp.TimeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Pu PanSheng, 2022/1/18
 * @version OPRA v1.0
 */
@RestController
public class QpsController {

    StaticNode staticNode=new StaticNode(1000,200, "pps");

    @RequestMapping("/qpstest")
    public void f2(HttpServletRequest request){

        System.out.println("-----");
        System.out.println(request.getParameterMap());
        staticNode.addPass();

    }


    @RequestMapping("/qps")
    public void f1(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.getWriter();
        httpServletResponse.getWriter().write(index());
        httpServletResponse.getWriter().flush();

    }
    @RequestMapping("/key")
    public void key(HttpServletRequest httpServletRequest,HttpServletResponse response) throws IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        List<String> keys = Arrays.asList(staticNode.getKey());
        String s = JSONObject.toJSONString(keys);
        response.setContentType("application/json");
        response.getWriter().write(s);
        response.getWriter().flush();
    }
    @RequestMapping("/value")
    public void value(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String key = request.getParameter("key");
        Entity entity=new Entity();
        entity.setCount(staticNode.qps(TimeUtil.currentTimeMillis(),0));
        entity.setError(0);
        entity.setKey(key);
        String s = JSONObject.toJSONString(entity);
        response.setContentType("application/json");
        response.getWriter().write(s);

    }

    private String index() {

        String html="<!DOCTYPE html>\n" +
                "<html>\n" +
                "\t<head>\n" +
                "\t\t<meta charset=\"utf-8\" />\n" +
                "\t\t<title>qps统计</title>\n" +
                "\t</head>\n" +
                "\t<style>\n" +
                "\t\t.list_nr_bg{\n" +
                "\t\t\tborder: aquamarine solid 1px;\n" +
                "\t\t\ttext-align: center;\n" +
                "\t\t}\n" +
                "\t\t.content{\n" +
                "\t\t\t\t \n" +
                "\t\t\t\t display: flex;\n" +
                "\t\t\t\t flex-direction: row;\n" +
                "\t\t\t\t \n" +
                "\t\t}\n" +
                "\t\t.left{\n" +
                "\t\t\t\t border-right: blanchedalmond solid 1px;\n" +
                "\t\t\t\t width: 350px;\n" +
                "\t\t}\n" +
                "\t\t.right{\n" +
                "\t\t\t\t margin-left: 100px;\n" +
                "\t\t\t\t width: 900px;\n" +
                "\t\t\t\t height:400px;\n" +
                "\t\t\t\t margin-top: 50px;\n" +
                "\t\t}\n" +
                "\t\t.lic{\n" +
                "\t\t\t    display: block;\n" +
                "\t\t\t    cursor: pointer;\n" +
                "\t\t\t    width: 300px;\n" +
                "\t\t\t    text-overflow: ellipsis;\n" +
                "\t\t\t    white-space: nowrap;\n" +
                "\t\t\t    text-overflow: ellipsis;\n" +
                "\t\t\t    overflow: hidden;\n" +
                "\t\t\t    word-break: break-all;\n" +
                "\t\t}\n" +
                "\t</style>\n" +
                "    <script src=\"https://cdn.staticfile.org/echarts/4.3.0/echarts.min.js\"></script>\n" +
                "\t<script>\n" +
                "\t\tvar url1=\"http://localhost:%s/key\";\n" +
                "\t\tvar url2=\"http://localhost:%s/value\";\n" +
                "\t\tvar myChart=undefined;\n" +
                "\t\tvar ketT=\"\";\n" +
                "\t\t\n" +
                "\t\t\n" +
                "\t\t\n" +
                "\t\t\n" +
                "\t\tvar optionInit = {};\n" +
                "\t\tfunction getData(url,param,fun){\n" +
                "\t\t    //创建xhr对象\n" +
                "\t\t    let xhr = new XMLHttpRequest();\n" +
                "\t\t    //设置请求方法\n" +
                "\t\t\tlet query=\"?k1=1\";\n" +
                "\t\t\tif(param!=null){\n" +
                "\t\t\t\tObject.keys(param).forEach(j=>{\n" +
                "\t\t\t\t query=query+\"&\"+j+\"=\"+param[j]\n" +
                "\t\t\t\t})\n" +
                "\t\t\t}\n" +
                "\t\t    xhr.open('GET',url+query);\n" +
                "\t\t    // 发送数据\n" +
                "\t\t    xhr.send(param)\n" +
                "\t\t    // 拿到服务端数据后执行相关操作\n" +
                "\t\t    xhr.onreadystatechange = function(){\n" +
                "\t\t        if(xhr.readyState==4){\n" +
                "\t\t\t\t\tfun(JSON.parse(xhr.responseText))\n" +
                "\t\t        }\n" +
                "\t\t    }\n" +
                "\t\t\n" +
                "\t\t}\n" +
                "\t\t\n" +
                "\t\tfunction addDepartment(id,content)\n" +
                "\t\t{\n" +
                "\t\t\tif(!content){\n" +
                "\t\t\t\treturn\n" +
                "\t\t\t}\n" +
                "\t\t\tvar x = document.getElementById(id);\n" +
                "\t\t\tvar l = x.childNodes.length;\n" +
                "\t\t\tvar li = document.createElement(\"li\");\n" +
                "\t\t\tli.attr=content\n" +
                "\t\t\tli.className = \"list_nr_bg\";\n" +
                "\t\t\tli.onclick=(e)=>{\n" +
                "\t\t\t\tlet kk=e.target.attr;\n" +
                "\t\t\t\tgetData(url2,{\"key\":content,\"type\":0},(da)=>{\n" +
                "\t\t\t\t\tlet tt=[]\n" +
                "\t\t\t\t\tlet optionT=undefined\n" +
                "\t\t\t\t\tif(content!=ketT){\n" +
                "\t\t\t\t\t\tlet g=[];\n" +
                "\t\t\t\t\t\tlet g2=[];\n" +
                "\t\t\t\t\t\tg.push([da.time,da.count])\n" +
                "\t\t\t\t\t\tg2.push([da.time,da.error])\n" +
                "\t\t\t\t\t\toptionT = {\n" +
                "\t\t\t\t\t\t            title: {\n" +
                "\t\t\t\t\t\t                text: content+' QPS统计数据'\n" +
                "\t\t\t\t\t\t            },\n" +
                "\t\t\t\t\t\t            tooltip: {},\n" +
                "\t\t\t\t\t\t            legend: {\n" +
                "\t\t\t\t\t\t                data:['QPS']\n" +
                "\t\t\t\t\t\t            },\n" +
                "\t\t\t\t\t\t            xAxis: {\n" +
                "\t\t\t\t\t\t\t\t\t\ttype: 'time',\n" +
                "\t\t\t\t\t\t\t\t\t\tsplitLine: {\n" +
                "\t\t\t\t\t\t\t\t\t\t   show: false\n" +
                "\t\t\t\t\t\t\t\t\t\t}\n" +
                "\t\t\t\t\t\t            },\n" +
                "\t\t\t\t\t\t            yAxis: {},\n" +
                "\t\t\t\t\t\t            series: [{\n" +
                "\t\t\t\t\t\t                name: 'qpsPass',\n" +
                "\t\t\t\t\t\t                type: 'line',\n" +
                "\t\t\t\t\t\t                data: [g]\n" +
                "\t\t\t\t\t\t            },{\n" +
                "\t\t\t\t\t\t                name: 'qpsError',\n" +
                "\t\t\t\t\t\t                type: 'line',\n" +
                "\t\t\t\t\t\t                data: [g2]\n" +
                "\t\t\t\t\t\t            }]\n" +
                "\t\t\t\t\t\t        };\n" +
                "\t\t\t\t\t   optionInit=optionT\n" +
                "\t\t\t\t\t   ketT=content\n" +
                "\t\t\t\t\t   myChart.setOption(optionT)\n" +
                "\t\t\t\t\t   return\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t})\n" +
                "\t\t\t};\n" +
                "\t\t\tli.innerHTML = \"<span class='lic'>\"+content+\"</span>\";\n" +
                "\t\t\tx.appendChild(li);\n" +
                "\t\t}\n" +
                "\t\t \n" +
                "\t\t let task=setInterval(()=>{\n" +
                "\t\t\t \n" +
                "\t\t \t\n" +
                "\t\t \tif(ketT){\n" +
                "\t\t \t\t\n" +
                "\t\t \t\tgetData(url2,{\"key\":ketT},(da)=>{\n" +
                "\t\t\t\t\t\n" +
                "\t\t \t\t\tlet optionT=optionInit\n" +
                "\t\t\t\t\tlet d1=optionT.series[0].data;\n" +
                "\t\t\t\t\tlet d2=optionT.series[1].data;\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\tif(d1.length>=30){\n" +
                "\t\t\t\t\t\td1=[]\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t\tif(d2.length>=30){\n" +
                "\t\t\t\t\t\td2=[]\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\tlet v=[da.time,da.count]\n" +
                "\t\t\t\t\tlet v2=[da.time,da.error]\n" +
                "\t\t\t\n" +
                "\t\t \t\t\td1.push(v)\n" +
                "\t\t\t\t\td2.push(v2)\n" +
                "\t\t\t\t\toptionT.series[0].data=d1\n" +
                "\t\t\t\t\toptionT.series[1].data=d2\n" +
                "\t\t \t\t\tmyChart.setOption(optionT)\n" +
                "\t\t \t\t\t\n" +
                "\t\t \t\t\t});\n" +
                "\t\t \t}\n" +
                "\t\t \t},1000);\n" +
                "\t\n" +
                "\t</script>\n" +
                "\t<body>\n" +
                "\t\t<div>\n" +
                "\t\t\t\n" +
                "\t\t\t<h1 align=\"center\" style=\"margin-bottom: 20px;border-bottom: aqua solid 1px;\">qps统计</h1>\n" +
                "\t\t\t\n" +
                "\t\t\t\n" +
                "\t\t\t<div class=\"content\">\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t<div id=\"ul\" class=\"left\">\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\t<ul id=\"li\">\n" +
                "\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\n" +
                "\t\t\t\t\t</ul>\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t\t<div id=\"qps\" class=\"right\" >\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\t\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t\n" +
                "\t\t\t   </div>\n" +
                "\t\t\t\t <script type=\"text/javascript\">\n" +
                "\t\t\t\t\t \n" +
                "\t\t\t\t\t      \n" +
                "\t\t\t\t\t    getData(url1,null,(da)=>{\n" +
                "\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\tda.forEach(key=>{\n" +
                "\t\t\t\t\t\t\t\taddDepartment(\"li\",key)\n" +
                "\t\t\t\t\t\t\t})\n" +
                "\t\t\t\t\t\t})\n" +
                "\t\t\t\t\t \n" +
                "\t\t\t\t        // 基于准备好的dom，初始化echarts实例\n" +
                "\t\t\t\t        myChart = echarts.init(document.getElementById('qps'));\n" +
                "\t\t\t\t \n" +
                "\t\t\t\t        // 指定图表的配置项和数据\n" +
                "\t\t\t\t        var option = {\n" +
                "\t\t\t\t            title: {\n" +
                "\t\t\t\t                text: 'qps 统计数据'\n" +
                "\t\t\t\t            },\n" +
                "\t\t\t\t            tooltip: {},\n" +
                "\t\t\t\t            legend: {\n" +
                "\t\t\t\t                data:['数据']\n" +
                "\t\t\t\t            },\n" +
                "\t\t\t\t            xAxis: {\n" +
                "\t\t\t\t                data: [],\n" +
                "\t\t\t\t\t\t\t\ttype: 'time'\n" +
                "\t\t\t\t            },\n" +
                "\t\t\t\t            yAxis: {},\n" +
                "\t\t\t\t            series: [{\n" +
                "\t\t\t\t                name: 'qpsPass',\n" +
                "\t\t\t\t                type: 'line',\n" +
                "\t\t\t\t                data: []\n" +
                "\t\t\t\t            },{\n" +
                "\t\t\t\t                name: 'qpsError',\n" +
                "\t\t\t\t                type: 'line',\n" +
                "\t\t\t\t                data: []\n" +
                "\t\t\t\t            }]\n" +
                "\t\t\t\t        };\n" +
                "\t\t\t\t \n" +
                "\t\t\t\t        // 使用刚指定的配置项和数据显示图表。\n" +
                "\t\t\t\t        myChart.setOption(option);\n" +
                "\t\t\t\t    </script>\n" +
                "\t\t\t\n" +
                "\t\t</div>\n" +
                "\t</body>\n" +
                "</html>\n";

        String t=String.valueOf(8090)+"";
        html=String.format(html,t,t);
        return html;
    }






}
