package com.soauth.api.client.openid.connect.service;

import com.soauth.api.client.model.ServerConfig;

/**
 *
 * @author zhoujie
 * @date 2017/11/6
 *
 * 连接(绑定)Openid发行方
 */
public interface ConnectServerConfigService {

    /**
     *  获取OIDC服务器配置
     * @param issuer  key
     * @return
     */
    ServerConfig loadServerConfig(String issuer);
}
