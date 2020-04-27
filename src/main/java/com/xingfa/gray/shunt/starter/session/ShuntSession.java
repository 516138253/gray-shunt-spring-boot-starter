package com.xingfa.gray.shunt.starter.session;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 精准分流session
 * @author: chenluqiang
 * @time: 2020-04-13:13:52
 */
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class ShuntSession implements Serializable {
    private static final long serialVersionUID = 9120765714832970813L;
    //集群主机当前60s内的请求数
    private Map<String,Integer> hostNumbers = new HashMap<>();
    //统计请求数
    private int currentCount=0;

    public Integer getHostNumber(String hostAddress){
        if(hostNumbers.get(hostAddress)==null){
            hostNumbers.put(hostAddress,0);
        }
        return hostNumbers.get(hostAddress);
    }

    public void addHostNumber(String hostAddress){
        if(hostNumbers.get(hostAddress)==null){
            hostNumbers.put(hostAddress,0);
        }
        hostNumbers.put(hostAddress, hostNumbers.get(hostAddress) + 1);
    }

    public void addCurrentCount(){
        currentCount++;
    }

    public int getCurrentCount(){
        return currentCount;
    }
}
