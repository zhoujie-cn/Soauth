package com.soauth.api.client.openid.connect.service;

import com.soauth.api.client.model.ServerConfig;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 *
 * @author zhoujie
 * @date 2017/11/29
 */
public interface ConnectAuthEndpointService {

    /**
     *  返回拼装完成的url参数
     * @param session
     * @param scope
     * @param clientid
     * @param responseType
     * @param redirectUri
     * @param options
     * @return
     */
 String connectauthuri(HttpSession session, String scope,String clientsecret, String clientid, String responseType, String redirectUri, Map<String,String> options);

    /**
     *
     * @return 返回一个服务器配置
     */
 ServerConfig getServerConfig();


}
