package com.xingfa.gray.shunt.starter.resttemplate.interceptor;

import com.xingfa.gray.shunt.starter.constant.StarterConstant;
import com.xingfa.gray.shunt.starter.properties.RequestHeaderProperties;
import com.xingfa.gray.shunt.starter.request.ForwardHttpRequest;
import com.xingfa.gray.shunt.starter.threadlocal.HttpRequestThreadLocal;
import com.xingfa.gray.shunt.starter.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URI;

/**
 * @description:resttemplate请求拦截器
 * @author: chenluqiang
 * @time: 2020-04-10:11:00
 */
@Component
@Configuration
@Slf4j
public class RestTemplateRibbonInterceptor implements ClientHttpRequestInterceptor {
    @Resource
    private RequestHeaderProperties restHeaderProperties;
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body
            , ClientHttpRequestExecution execution) throws IOException {
        //加入版本、机器码到请求头
        HttpHeaders headers = request.getHeaders();
        if(restHeaderProperties!=null&&restHeaderProperties.getHeaders()!=null
                &&!restHeaderProperties.getHeaders().isEmpty()){
            headers.addAll(restHeaderProperties.getHeaders());
        }
        //存储请求头的参数
        URI uri = request.getURI();
        ForwardHttpRequest httpRequest = new ForwardHttpRequest();
        httpRequest.setUri(uri);
        httpRequest.setServiceId(uri.getHost());
        httpRequest.setParameters(WebUtils.getQueryParams(uri.getQuery()));
        httpRequest.setMethod(request.getMethod().name());
        httpRequest.addHeaders(headers.toSingleValueMap());
        httpRequest.setAttribute(StarterConstant.REQUEST_ATTRIBUTE_REST_TEMPLATE_REQUEST, request);
        HttpRequestThreadLocal.setRestTemplateRequest(httpRequest);
        return execution.execute(request, body);
    }
}
