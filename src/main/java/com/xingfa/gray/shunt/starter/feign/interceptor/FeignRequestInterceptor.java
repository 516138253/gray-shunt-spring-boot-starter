package com.xingfa.gray.shunt.starter.feign.interceptor;

import com.xingfa.gray.shunt.starter.constant.StarterConstant;
import com.xingfa.gray.shunt.starter.properties.RequestHeaderProperties;
import com.xingfa.gray.shunt.starter.request.ForwardHttpRequest;
import com.xingfa.gray.shunt.starter.threadlocal.HttpRequestThreadLocal;
import com.xingfa.gray.shunt.starter.utils.WebUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:fegin全局请求拦截器
 * @author: chenluqiang
 * @time: 2020-04-21:16:38
 */
@Component
@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor {
    @Resource
    private RequestHeaderProperties restHeaderProperties;
    @Override
    public void apply(RequestTemplate template) {
        //加入版本、机器码到请求头
        if(restHeaderProperties!=null) {
            MultiValueMap<String, String> propertiesHeaders = restHeaderProperties.getHeaders();
            if(propertiesHeaders!=null&&!propertiesHeaders.isEmpty()) {
                propertiesHeaders.forEach ((k, v) -> {
                    if(v!=null&&v.size()>0) {
                        template.header(k,v.get(0));
                    }
                });
            }
        }
        //统一请求头数据 key:map
        Map<String,String> headers =new HashMap<>();
        template.headers().forEach((k, v) -> {
            if(v!=null&&v.size()>0) {
                headers.put(k,((List)v).get(0).toString());
            }
        });
        //转发请求头的参数
        ForwardHttpRequest httpRequest = new ForwardHttpRequest();
        httpRequest.setParameters(WebUtils.getQueryParams(template.queryLine()));
        httpRequest.setMethod(template.request().httpMethod().name());
        httpRequest.addHeaders(headers);
        httpRequest.setAttribute(StarterConstant.REQUEST_ATTRIBUTE_FEIGN_REQUEST, template.request());
        HttpRequestThreadLocal.setRestTemplateRequest(httpRequest);
    }
}