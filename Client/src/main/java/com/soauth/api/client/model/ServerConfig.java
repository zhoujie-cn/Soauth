package com.soauth.api.client.model;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author zhoujie
 * @date 2017/11/16
 *
 *
 * 以下列举的参数只包含本系统Client端需要的参数,更多的参数请访问Openid Connect 官网文档
 *
 * http://openid.net/specs/openid-connect-core-1_0.html
 *
 */
public class ServerConfig {

    /**
     * Op的地址
     */
    @Getter
    @Setter
    private String issuer;

    /**
     * 可选参数, OP 的身份验证和授权的端点uri
     */
    @Getter
    @Setter
    private String authorizationEndpointUri;

    /**
     * 必需的,  rp通过uri获取op的jwks
     */
    @Getter
    @Setter
    private String jwksUri;

    /**
     * 可选参数, op的令牌获取uri
     */
    @Getter
    @Setter
    private String tokenEndpointUri;

    @Getter
    @Setter
    private String userInfoUri;

}
