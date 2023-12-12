package com.pps.service;

import com.pps.es.ReportInfo;
import com.pps.vo.ReportReq;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: pps
 * @Date: 2023/12/12 18:31
 * @Description:
 **/
@Service
public class EsService {


    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public Page<ReportInfo> reportInfo(ReportReq req) {


        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if(req.getType()!=null){
            boolQueryBuilder.filter(new TermQueryBuilder("type",req.getType()));
        }
        if(req.getArea()!=null){
            boolQueryBuilder.must(new MatchQueryBuilder("area",req.getArea()));
        }
        if(req.getKeyword()!=null){
            List<QueryBuilder> builders = boolQueryBuilder.should();
            builders.add(new MatchQueryBuilder("detail",req.getKeyword()));
            builders.add(new MatchQueryBuilder("title",req.getKeyword()));
        }

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(PageRequest.of(req.getPage(), req.getSize())) // 设置分页信息
                .build();

        // 执行查询
        Page<ReportInfo> searchHits = elasticsearchRestTemplate.queryForPage(searchQuery, ReportInfo.class);

        return searchHits;

    }
}
