package com.xingfa.gray.shunt.starter.request;


import lombok.Getter;
import lombok.Setter;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:转发请求
 * @author: chenluqiang
 * @time: 2020-04-10 17:04
 */
public class ForwardRequest {
    @Setter
    @Getter
    private String serviceId;

    @Setter
    @Getter
    private URI uri;

    private Map<String, Object> attributes = new HashMap<>(32);

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }
}
