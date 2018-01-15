package com.soauth.api.client.oauth.service;

/**
 *
 * @author zhoujie
 * @date 2017/11/16
 */
public interface ConfigurationService {

    /**
     *  获取accessToken中issuer地址
     * @param accessToken
     * @return
     */
    String getissuerUrl(String accessToken);

}
