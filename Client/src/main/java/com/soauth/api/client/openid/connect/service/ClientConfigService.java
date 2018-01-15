package com.soauth.api.client.openid.connect.service;

import com.soauth.core.vo.oauth2.ClientDetails;

/**
 *
 * @author zhoujie
 * @date 2017/12/3
 */
public interface ClientConfigService {

    /**
     * 获取客户端配置(静态的,以及远程从OP获取的)
     * @param issuer  OP端点
     * @return
     */
   ClientDetails getClientConfig(String  issuer);
}
