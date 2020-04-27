package com.xingfa.gray.shunt.starter.utils;

import com.xingfa.gray.shunt.starter.constant.StarterConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @description:设置应用注册在eureka上的metadata
 * @author: chenluqiang
 * @time: 2020-04-24:15:45
 */
@Component
@Slf4j
public class EurekaMetadataUtil {
    @Value(StarterConstant.PATH_EUREKA_SERVER_URL_VALUE)
    private String eurekaUrl;
    @Value(StarterConstant.PATH_APP_NAME_VALUE)
    private String appName;
    @Value(StarterConstant.PATH_EUREKA_INSTANCE_ID__VALUE)
    private String instanceId;
    @Resource(name = "metaDataRestTemplate")
    private RestTemplate metaDataRestTemplate;

    ///demo: http://192.168.56.102:8888/eureka/apps/PROVIDER-SERVICE/localhost:provider-service:8006/metadata?weight=
    public void addEurekaMetadata(String key,String value) {
        if(StringUtils.isBlank(instanceId)){
            log.error(appName+":metadata添加失败!没有找到对应的instanceId");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(eurekaUrl).append("apps/")
                .append(appName).append("/")
                .append(instanceId)
                .append("/metadata?").append(key).append("=").append(value);
        String url = stringBuilder.toString();
        log.warn(url);
        try {
            metaDataRestTemplate.put(url, String.class);
        }catch (Exception e){
            //已知异常处理
            log.error(appName+":metadata添加失败!没有找到对应的主机（404）");
        }
    }
}
