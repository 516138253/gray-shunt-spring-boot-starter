package com.xingfa.gray.shunt.starter.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.event.InstancePreRegisteredEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @description:客户端连接eureka监听器
 * @author: chenluqiang
 * @time: 2020-04-25:14:24
 */
@Component
@Slf4j
class ClientConnectEurekaListener implements ApplicationListener<InstancePreRegisteredEvent> {
    /*@Override
    public void onApplicationEvent(InstanceRegisteredEvent instanceRegisteredEvent) {
        log.warn("==============+++++++++++===========================");
    }*/

    @Override
    public void onApplicationEvent(InstancePreRegisteredEvent instancePreRegisteredEvent) {
        log.warn("==============+++++++++++===========================");
    }
   /* ApplicationInfoManager.StatusChangeListener eurekaListener = null;
    @Resource
    private EurekaClient eurekaClient;

    @PostConstruct
    public void init() {
        eurekaListener = new ApplicationInfoManager.StatusChangeListener() {
            @Override
            public String getId() {
                return "ClientConnectEurekaListener";
            }
            @Override
            public void notify(StatusChangeEvent statusChangeEvent) {
                // 当前状态为UP， 之前的状态为STARTING
                log.warn(statusChangeEvent.getStatus()+"=============");
                if(InstanceInfo.InstanceStatus.UP == statusChangeEvent.getStatus()){
                    if (InstanceInfo.InstanceStatus.STARTING == statusChangeEvent.getPreviousStatus()) {
                        log.warn("ddddddddddddddddddddddddddddddddddd");
//                    ApplicationInfoManager.getInstance().unregisterStatusChangeListener(eurekaListener.getId());
                        eurekaListener = null;
                    }else if(InstanceInfo.InstanceStatus.OUT_OF_SERVICE == statusChangeEvent.getPreviousStatus()){
                        log.warn("wwwwwwwwwwwwwwwwwwwwwwwwwww");
                    }
                }
            }
        };
        eurekaClient.getApplicationInfoManager().registerStatusChangeListener(eurekaListener);
    }*/
}