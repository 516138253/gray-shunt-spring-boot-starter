package com.xingfa.gray.shunt.starter.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 存储请求头信息，跨线程转发给ribbon
 * @description:
 * @author: chenluqiang
 * @time: 2020-04-10 11:25
 */
@Getter
@Setter
public class ForwardHttpRequest extends ForwardRequest {
    //请求头等信息
    private Map<String, String> headers = new HashMap<>();
    private String method;
    private Map<String, String> parameters = new HashMap<>();
    private byte[] body;

    public void addHeaders(Map<String,String> headers) {
        if (headers == null || headers.isEmpty()) {
            return;
        }
        headers.forEach((k, v) -> {
            this.headers.put(k,v);
        });
    }

    public void addParameters(Map<String,String> parameters) {
        if (CollectionUtils.isEmpty(parameters)) {
            return;
        }
        parameters.forEach((k, v) -> {
            this.parameters.put(k, v);
        });
    }

    public void addHeader(String name, String value) {
       headers.computeIfAbsent(name, (k) ->value);
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public String getParameter(String name) {
        return parameters.get(name);
    }
}
