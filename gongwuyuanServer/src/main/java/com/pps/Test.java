package com.pps;

import com.alibaba.fastjson.JSON;
import com.pps.es.ReportInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @Author: pps
 * @Date: 2023/12/12 14:15
 * @Description:
 **/
@Slf4j
public class Test {
    static CloseableHttpClient httpClient = HttpClients.createDefault();
    public static String get(String url, Map<String,String>... params)  {


        if (params!=null&&params.length>0 ) {
            url=url+"?1=1";
            for (Map<String, String> param : params) {
                for (Map.Entry entry : param.entrySet()) {
                   url=url+"&"+entry.getKey()+"="+entry.getValue();
                }
            }
        }

        HttpGet httpGet = new HttpGet(url);
        //3.httpclient执行(httpget)请求
        CloseableHttpResponse response = null;    //执行http get请求
        try {
            response = httpClient.execute(httpGet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //4.获取返回的实体(entity)
        HttpEntity entity = response.getEntity();
        String context = null;    //获取网页内容
        try {
            context = EntityUtils.toString(entity, "utf-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //5.关闭资源
        try {
            response.close();    //response关闭
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return context;
    }
    static volatile RestHighLevelClient client=null;
    public static RestHighLevelClient getEsClient(){
        if(client!=null){
            return client;
        }
        String endpoints="192.168.56.10:9200";

        int ind= endpoints.lastIndexOf(":");
        final String host =endpoints.substring(0,ind);
        final int port = Integer.parseInt(endpoints.substring(ind+1));
        RestClientBuilder builder = RestClient.builder(new HttpHost(host, port))
                .setRequestConfigCallback(config -> {
                    config.setConnectTimeout(15000);
                    config.setSocketTimeout(5000);
                    return config;
                });

        //保活策略
        builder.setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                .setDefaultIOReactorConfig(IOReactorConfig.custom()
                        .setSoKeepAlive(true)
                        .build()));


        client=new RestHighLevelClient(builder);

        return client;
    }
    public static void main(String[] args) throws Exception {

        RestHighLevelClient esClient = getEsClient();
        String html=get("http://job.mohrss.gov.cn/cjobs/institution/listInstitution",new HashMap(){{
            put("pageNo",1);
            put("origin","四川");
        }});

        Document doc = Jsoup.parse(html);

        Elements elements = doc.selectXpath("//*[@id=\"citys\"]");
        List<Element> city=new ArrayList<>();
        for (Element element : elements) {
            Elements span = element.getElementsByTag("span");
            city= span.stream().map(s -> s.getElementsByTag("a").get(0)).collect(Collectors.toList());
            System.out.println(city);
        }

        int indexC=0;
        for (Element element : city) {

            Attributes attributes = element.attributes();

            String onclick = attributes.get("onclick");

            String ori = onclick.substring("hotcitysearch(".length()+1, onclick.length() - 3);
            if(ori==null||ori.length()==0){
                continue;
            }

            int page=-1;
            int nowPage=1;
            while (page==-1||nowPage<=page){

                int finalNowPage = nowPage;

                log.info("开始执行第{}页爬取！ ", finalNowPage);
                String htmlReport=get("http://job.mohrss.gov.cn/cjobs/institution/listInstitution",new HashMap(){{
                    put("pageNo", finalNowPage);
                    put("origin",ori);
                }});

                Document tableList = Jsoup.parse(htmlReport);
                Element reportList = tableList.selectXpath("/html/body/div[1]/div[3]/div[1]/div[3]/div[1]/div/div/div").get(0);

                Elements tables = reportList.getElementsByTag("table");

                for (int i = 0; i < tables.size(); i++) {

                    Element table = tables.get(i);
                    if(!table.hasClass("table_4")){
                        continue;
                    }

                    String url= null;
                    try {
                        Elements tds = table.getElementsByTag("td");

                        Element titleE=tds.get(1);

                        String title=titleE.getElementsByTag("a").get(0).html();

                        Element publishTimeE=tds.get(2);

                        String publishTime=publishTimeE.html();


                        String count=tds.get(3).html();


                        Element detailUrlE=tds.get(4);

                        url = "http://job.mohrss.gov.cn"+detailUrlE.getElementsByTag("a").get(0).attr("href");

                        String htmlDetail=get(url,null);

                        ReportInfo reportInfo = new ReportInfo()
                                .setType("事业单位")
                                .setArea(ori)
                                .setTitle(title)
                                .setDetail(htmlDetail)
                                .setAddTime(new Date())
                                .setPublishTime(new SimpleDateFormat("yyyy-MM-dd").parse(publishTime))
                                .setZhaopinCount(Integer.parseInt(count));


                        IndexRequest request = new IndexRequest("report_info","_doc"); // 替换为你的索引名称
                        //request.id("your_document_id"); // 可选，如果不设置，Elasticsearch 会自动生成一个唯一的 ID
                        String jsonString = JSON.toJSONString(reportInfo);
                        request.source(jsonString, XContentType.JSON);

                        // 执行 IndexRequest
                        IndexResponse response = esClient.index(request, RequestOptions.DEFAULT);
                        // 处理响应
                        String index = response.getIndex();
                        String id = response.getId();

                        /*  if (response.getResult() == org.elasticsearch.action.DocWriteResponse.Result.CREATED) {
                            System.out.println("Document created in index " + index + " with ID " + id);
                        } else if (response.getResult() == org.elasticsearch.action.DocWriteResponse.Result.UPDATED) {
                            System.out.println("Document updated in index " + index + " with ID " + id);
                        }*/
                    } catch (Exception e) {
                        log.error("解析table:{} 失败！",table,e);
                    }

                }

                if(page==-1) {
                    Document parse = Jsoup.parse(htmlReport);
                    Elements pageEs = parse.selectXpath("/html/body/div[1]/div[3]/div[1]/div[3]/div[1]/div/div/div/div/table/tbody/tr/td/table/tbody/tr/td[13]");
                    Elements pages = pageEs.get(0).getElementsByTag("b");
                    if(pages.size()>2) {
                        String total = pages.get(2).html();
                        page=Integer.parseInt(total);
                    }
                }

                nowPage++;

                Thread.sleep(800);
            }


            if(indexC==0) {
                break;
            }

            indexC++;
        }


//1.创建httpclient实例

        //2.创建httpget实例(请求)


    }
}
