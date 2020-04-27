package com.xingfa.gray.shunt.starter.properties;

import com.xingfa.gray.shunt.starter.constant.StarterConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

/**
 * @description: 读取使用该starter应用的配置。
 * @author: chenluqiang
 * @time: 2020-04-20:14:04
 */
@Data
//@Slf4j
@Component
@ConfigurationProperties(prefix = StarterConstant.PATH_PREFIX)
//@Conditional(RequestHeaderCondition.class)
public class RequestHeaderProperties{
    private MultiValueMap<String,String> headers;

    /*@Autowired implements ApplicationRunner
    private EurekaMetadataUtil eurekaMetadataUtil;
    @Override
    public void run(ApplicationArguments args){
        if(headers!=null&&!headers.isEmpty()&&headers.get(StarterConstant.KEY_REQUEST_VERSION)!=null){
            eurekaMetadataUtil.addEurekaMetadata(StarterConstant.KEY_REQUEST_VERSION
                    ,headers.get(StarterConstant.KEY_REQUEST_VERSION).get(0));
        }
    }*/
}
