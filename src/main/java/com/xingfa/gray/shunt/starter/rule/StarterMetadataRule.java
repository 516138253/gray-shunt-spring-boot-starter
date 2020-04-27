package com.xingfa.gray.shunt.starter.rule;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import com.xingfa.gray.shunt.starter.constant.StarterConstant;
import com.xingfa.gray.shunt.starter.request.ForwardHttpRequest;
import com.xingfa.gray.shunt.starter.session.ShuntSession;
import com.xingfa.gray.shunt.starter.threadlocal.HttpRequestThreadLocal;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description
 * @author chenluqiang
 * @date 2020-04-02
 */
public class StarterMetadataRule extends ZoneAvoidanceRule {
    Logger logger = LoggerFactory.getLogger(StarterMetadataRule.class);
    @Autowired
    private ShuntSession shuntSession;
    @Value(StarterConstant.PATH_EUREKA_ENVIRONMENT_VALUE)
    private String environment;
    @Value(StarterConstant.PATH_APP_NAME_VALUE)
    private String appName;
    @Value(StarterConstant.PATH_EUREKA_INSTANCE_ID__VALUE)
    private String instanceId;
    @Value("${eureka.instance.ip-address}")
    private String localIpAddress;
    @Override
    public Server choose(Object key) {
        //从RestTemplate中获取请求参数
        ForwardHttpRequest templateRequest = HttpRequestThreadLocal.getRestTemplateRequest();
        //1.从请求头获取version信息
        String version = templateRequest.getHeader(StarterConstant.KEY_REQUEST_VERSION);
        //2.获取服务实例列表
        List<Server> serverList = getPredicate().getEligibleServers(this.getLoadBalancer().getAllServers(), key);
        //3.循环serverList，选择匹配服务并返回
        List<Server> notVersionSeverList = new ArrayList<>();
        List<Server> versionSeverList = new ArrayList<>();

        ///如果有环境走环境, //没有环境、缺少依赖starter、starter没环境配置,走开发环境
        if (StringUtils.isBlank(this.environment)) {
            this.environment = StarterConstant.KEY_ENVIRONMENT_DEV;
        }
        for(Server server : serverList){
            String host = server.getHost();
            InstanceInfo instanceInfo = ((DiscoveryEnabledServer) server).getInstanceInfo();
            Map<String, String> metadata = instanceInfo.getMetadata();
            String environment = metadata.get(StarterConstant.KEY_EUREKA_ENVIRONMENT);
            //匹配同ip地址的机器
            if(this.environment == StarterConstant.KEY_ENVIRONMENT_DEV
                    &&StringUtils.isNotBlank(host)&&host.equals(localIpAddress)){
                versionSeverList.add(server);
            }else if(this.environment == StarterConstant.KEY_ENVIRONMENT_TEST
                    &&this.environment.equals(environment.trim())){
                versionSeverList.add(server);
            }
        }

        ///如果开发环境 没有匹配上主机的，则匹配其他环境的主机
        if(this.environment.equals(StarterConstant.KEY_ENVIRONMENT_DEV)
                &&versionSeverList.size()==0) {
            for(Server server : serverList){
                InstanceInfo instanceInfo = ((DiscoveryEnabledServer) server).getInstanceInfo();
                Map<String, String> metadata = instanceInfo.getMetadata();
                String environment = metadata.get(StarterConstant.KEY_EUREKA_ENVIRONMENT);
                if(environment.equals(StarterConstant.KEY_ENVIRONMENT_TEST)){
                    versionSeverList.add(server);
                }
            }
        }

        //4.按比例获取灰度主机或者非灰度主机
        Server server = null;
        try{
            if(!StringUtils.isEmpty(version)
                    &&!versionSeverList.isEmpty()&&versionSeverList.size()!=0) {
                  server = this.chooseExactRateMetadataPredicate(versionSeverList);
            }else {
                  server = this.chooseExactRateMetadataPredicate(notVersionSeverList);
            }
        }catch (IllegalStateException e){
            //采取默认值不处理
            logger.debug("这里异常不处理,变量:{}采取默认值。", StarterConstant.KEY_SERVER);
        }
        return server;
    }

    /**
     * 精准分流
     * @param serverList
     * @return
     */
    private Server chooseExactRateMetadataPredicate(List<Server> serverList) {
        //如果只有一个实例返回
        if(serverList!=null&&serverList.size()==1){
            return serverList.get(0);
        }
        //获取服务实例列表
        List<Map<String,Object>> weightHosts = new ArrayList<>();
        for (Server server : serverList) {
            Map<String, String> metadata = ((DiscoveryEnabledServer) server).getInstanceInfo().getMetadata();
            int weight=0;
            try {
                weight = Integer.parseInt(metadata.get(StarterConstant.KEY_WEIGHT));
            } catch (Exception e) {
                // 无需处理
                logger.debug("这里异常不处理,变量:{}采取默认值。", StarterConstant.KEY_WEIGHT);
            }
            if(weight!=0){
                //记录主机信息
                Map<String,Object> weightHostMap = new HashMap<>();
                weightHostMap.put(StarterConstant.KEY_HOST_PORT,server.getHostPort());
                weightHostMap.put(StarterConstant.KEY_WEIGHT,metadata.get(StarterConstant.KEY_WEIGHT));
                weightHostMap.put(StarterConstant.KEY_SERVER,server);
                weightHosts.add(weightHostMap);
            }
        }
        //如果serverList有主机，没有比例设置，则默认轮询机制
        if(serverList!=null&&serverList.size()>0&&weightHosts==null||weightHosts.isEmpty()){
            return getPredicate().chooseRoundRobinAfterFiltering(serverList).get();
        }else {
            //统计比例总值
            int weightCount = 0;
            for (Map map : weightHosts) {
                weightCount += Integer.parseInt(map.get(StarterConstant.KEY_WEIGHT).toString());
            }
            //记录请求次数
            shuntSession.addCurrentCount();
            int currentCount = shuntSession.getCurrentCount();
            for (int index = 0; index < weightHosts.size(); index++) {
                int weight = Integer.parseInt(weightHosts.get(index).get(StarterConstant.KEY_WEIGHT).toString());
                String hostPort = weightHosts.get(index).get(StarterConstant.KEY_HOST_PORT).toString();
                Integer hostNumber = shuntSession.getHostNumber(hostPort);

                //计算比例值,是否满足
                if ((hostNumber * 1.00) / currentCount < (weight * 1.00) / weightCount) {
                    shuntSession.addHostNumber(hostPort);
                    return ((Server) weightHosts.get(index).get(StarterConstant.KEY_SERVER));
                } else {
                    continue;
                }
            }
        }
        return null;
    }
}
