package com.xingfa.gray.shunt.starter.constant;

/**
 * @description: starter常量
 * @author: chenluqiang
 * @time: 2020-04-21:16:15
 */
public class StarterConstant {
    /*服务跨环境去访问的目标环境  的metadata key*/
    public static final String KEY_TARGET_APP_NAME_VERSION = "target-appName-version";
    //切割符号
    public static final String KEY_TARGET_SPLIT_SPOT = ",";
    /*环境Key*/
    public static final String KEY_EUREKA_ENVIRONMENT ="eureka-environment";
    //开发
    public static final String KEY_ENVIRONMENT_DEV ="dev";
    //测试
    public static final String KEY_ENVIRONMENT_TEST ="test";
    //读取当前环境value路径
    public static final String PATH_EUREKA_ENVIRONMENT_VALUE =
            "${eureka.instance.metadata-map."+KEY_EUREKA_ENVIRONMENT+"}";
    //instanceId
    public static final String PATH_EUREKA_INSTANCE_ID__VALUE =
            "${eureka.instance.instance-id:''}" ;
    //应用名称,默认为UNKNOWN。
    public static final String PATH_APP_NAME_VALUE =
            "${spring.application.name:UNKNOWN}";
    public static final String PATH_EUREKA_SERVER_URL_VALUE =
            "${eureka.client.serviceUrl.defaultZone:''}";


    /*存储的key值*/
    public static final String KEY_MACHINE_CODE="machineCode";
    //请求的version
    public static final String KEY_REQUEST_VERSION="request-version";
    //接受请求的version
    public static final String KEY_RECEIVE_VERSION="receive-version";
    public static final String KEY_WEIGHT = "weight";
    public static final String KEY_HOST_PORT = "hostPort";
    public static final String KEY_SERVER = "server";

    /*版本号、机器码的配置名称路径 、 前缀*/
    public static final String PATH_PREFIX = "gray-shunt-starter";
    public static final String PATH_MACHINE_CODE = PATH_PREFIX +".headers.machineCode";
    public static final String PATH_REQUEST_VERSION = PATH_PREFIX +".headers.request-version";

    /**默认配置**/
    public static final String DEFAULT_PROPERTIES_FILE_PATH = "/request-header.properties";

    /*存储resttemplate 和 feign请求 的 key名称 */
    public static final String REQUEST_ATTRIBUTE_REST_TEMPLATE_REQUEST = "restTemplate.request";
    public static final String REQUEST_ATTRIBUTE_FEIGN_REQUEST = "feign.request";
}
