package com.xingfa.gray.shunt.starter;

import com.netflix.loadbalancer.IRule;
import com.xingfa.gray.shunt.starter.feign.interceptor.FeignRequestInterceptor;
import com.xingfa.gray.shunt.starter.resttemplate.interceptor.RestTemplateRibbonInterceptor;
import com.xingfa.gray.shunt.starter.rule.StarterMetadataRule;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * @description: starter 自动配置入口
 * @author: chenluqiang
 * @time: 2020-04-21 16:11
 */
@Slf4j
@Configuration
@ComponentScan("com.xingfa.gray.shunt.starter")
public class GrayShuntAutoConfiguration {
    /***
     * 读取自定义starter版本号、机器码参数配置。
     */
    /*@Bean
    @Conditional(NotRequestHeaderCondition.class)
    public RequestHeaderProperties initHeaderProperties() throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = Object.class.getResourceAsStream(StarterConstant.DEFAULT_PROPERTIES_FILE_PATH);
        //加载starter默认配置
        properties.load(inputStream);
        RequestHeaderProperties headerProperties = new RequestHeaderProperties();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(StarterConstant.KEY_MACHINE_CODE,properties.getProperty(StarterConstant.PATH_MACHINE_CODE));
        headers.add(StarterConstant.KEY_REQUEST_VERSION,properties.getProperty(StarterConstant.PATH_REQUEST_VERSION));
        headerProperties.setHeaders(headers);
        log.info("==================使用默认的配置=======================");
        return headerProperties;
    }*/

    // 声明为Bean，方便应用内使用同一实例
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        log.info("+++++++++++++++++++++++++++注册Starter RestTemplate++++++++++++++++++++++++++++++++");
        RestTemplate template = new RestTemplate();
        template.setInterceptors(Collections.singletonList(paramInterceptor()));
        return template;
    }

    @Bean("metaDataRestTemplate")
    public RestTemplate metaDataRestTemplate() {
        return new RestTemplate();
    }

    /**
     * RestTemplate全局请求拦截器。
     * @return
     */
    @Bean
    public RestTemplateRibbonInterceptor paramInterceptor() {
        return new RestTemplateRibbonInterceptor();
    }

    /**
     * Feign全局请求拦截器。
     */
    @Bean
    public RequestInterceptor requestInterceptor(){
        return new FeignRequestInterceptor();
    }

    /**
     * 自定义ribbon全局负载均衡策略
     * @return
     */
    @Bean
    @Scope("prototype")
    public IRule GrayExactRateMetadataRule()
    {
//        return new GrayExactRateMetadataRule();
        return new StarterMetadataRule();
    }
}
