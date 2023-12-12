package com.pps.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchEntityMapper;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.EntityMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

/**
 * @author He Changjie on 2022/6/6 14:02
 */
@Configuration
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration{
    /** ES链接一 host:port */
    @Value("${spring.data.elasticsearch.client.urls}")
    private String endpoints;

    /** 连接elasticsearch超时时间 */
    @Value("${spring.data.elasticsearch.client.reactive.connection-timeout:30000}")
    private Integer connectTimeout;
    /** 套接字超时时间 */
    @Value("${spring.data.elasticsearch.client.reactive.socket-timeout:5000}")
    private Integer socketTimeout;

    /** 用户名 */
    @Value("${spring.data.elasticsearch.client.reactive.username:}")
    private String username;
    /** 密码 */
    @Value("${spring.data.elasticsearch.client.reactive.password:}")
    private String password;

    @Bean("elasticsearchRestTemplate")
    @Primary
    public ElasticsearchRestTemplate elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }

    /**
     * 构建方式一
     */
    @Bean("restHighLevelClient")
    @Primary
    @Override
    public RestHighLevelClient elasticsearchClient() {
        // 初始化 RestClient, hostName 和 port 填写集群的内网 IP 地址与端口
        int ind= endpoints.lastIndexOf(":");
        final String host =endpoints.substring(0,ind);
        final int port = Integer.parseInt(endpoints.substring(ind+1));
        RestClientBuilder builder = RestClient.builder(new HttpHost(host, port))
                .setRequestConfigCallback(config -> {
                    config.setConnectTimeout(connectTimeout);
                    config.setSocketTimeout(socketTimeout);
                    return config;
                });

        //保活策略
        builder.setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                .setDefaultIOReactorConfig(IOReactorConfig.custom()
                        .setSoKeepAlive(true)
                        .build()));
        // 设置认证信息
        if(username!=null&&username.length()>0) {
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
            builder.setHttpClientConfigCallback(httpAsyncClientBuilder -> {
                httpAsyncClientBuilder.disableAuthCaching();
                return httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            });
        }

        return new RestHighLevelClient(builder);
    }

  /*  @Bean("zeekElasticsearchTemplate")
    public ElasticsearchRestTemplate ZeekElasticsearchTemplate() {
        return new ElasticsearchRestTemplate(zeekRestHighLevelClient());
    }
*/
    /**
     * 构建方式二
     */
/*    @Bean("zeekRestHighLevelClient")
    public RestHighLevelClient zeekRestHighLevelClient() {
        HttpHeaders defaultHeaders = new HttpHeaders();
        defaultHeaders.setBasicAuth(username, password);
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(endpointsZeek)
                .withConnectTimeout(connectTimeout)
                .withSocketTimeout(socketTimeout)
                .withDefaultHeaders(defaultHeaders)
                .withBasicAuth(username, password)
                .build();
        return RestClients.create(clientConfiguration).rest();
    }*/

    @Bean
    @Override
    public EntityMapper entityMapper() {
        ElasticsearchEntityMapper entityMapper = new ElasticsearchEntityMapper(elasticsearchMappingContext(),
                new DefaultConversionService());
        entityMapper.setConversions(elasticsearchCustomConversions());
        return entityMapper;
    }
}