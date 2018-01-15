package com.soauth.api.client.openid.connect.service.impl;

import com.soauth.api.client.model.ServerConfig;
import com.soauth.api.client.openid.connect.service.ConnectServerConfigService;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 *
 * @author zhoujie
 * @date 2017/11/16
 *
 *
 * <bean id="staticConnectServer" class="com.soauth.api.client.openid.connect.service.impl.StaticConnectServerConfigServiceimpl" >
 *
 */
public class StaticConnectServerConfigServiceimpl implements ConnectServerConfigService {

    @Getter
    @Setter
    private Map<String,ServerConfig> serverConfigMap;


    @Override
    public ServerConfig loadServerConfig(String issuer) {
        if (serverConfigMap == null || serverConfigMap.isEmpty()) {
            throw new IllegalArgumentException("ServerConfigMap 为空或为Null.");
        }else{
            serverConfigMap.forEach((k,v)->{
                System.out.println("k="+k+"v"+v);
            });
        }
        return serverConfigMap.get(issuer);
    }

    @PostConstruct
    public void verifyEmptyornull(){
        if (serverConfigMap == null || serverConfigMap.isEmpty()) {
            throw new IllegalArgumentException("ServerConfigMap 为空或为Null.");
        }
    }


}
