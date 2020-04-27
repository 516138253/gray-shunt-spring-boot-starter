package com.xingfa.gray.shunt.starter.threadlocal;

import com.xingfa.gray.shunt.starter.request.ForwardHttpRequest;
import lombok.Getter;

/**
 * @description:请求头存储，在子线程中使用
 * @author: chenluqiang
 * @time: 2020-04-10:11:47
 */
@Getter
public class HttpRequestThreadLocal {
    private static final ThreadLocal<ForwardHttpRequest> requestLocal = new ThreadLocal<>();

    public static ForwardHttpRequest getRestTemplateRequest(){
        return requestLocal.get();
    }

    public static void setRestTemplateRequest(ForwardHttpRequest request){
        requestLocal.set(request);
    }
}
