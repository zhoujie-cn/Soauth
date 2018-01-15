package com.soauth.server.oauth.authorize;

/**
 *
 * @author zhouejie
 * @date 2017/12/4
 *
 * 服务器端配置信息
 *
 * server-config.xml
 */

public class ServerConfig {


    private String issuer;

    private String jwkUri;

    public String getIssuer() {
        return issuer;
    }

    public String getJwkUri() {
        return jwkUri;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public void setJwkUri(String jwkUri) {
        this.jwkUri = jwkUri;
    }
}
