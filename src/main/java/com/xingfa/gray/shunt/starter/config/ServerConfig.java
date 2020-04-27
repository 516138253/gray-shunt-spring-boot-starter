/*
package com.xingfa.gray.shunt.starter.config;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
*/
/**
 * @description:获取应用的IP和端口
 * @author: chenluqiang
 * @time: 2020-04-23:15:22
 *//*

@Component
public class ServerConfig implements ApplicationListener<WebServerInitializedEvent> {
    public int getServerPort() {
        return serverPort;
    }
    private int serverPort;

    public String getHostPort() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return address.getHostAddress()+":"+this.serverPort;
    }

    public String getHost() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return address.getHostAddress();
    }

    public int getPort() {
        return this.serverPort;
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        serverPort = event.getWebServer().getPort();
    }
}

*/
