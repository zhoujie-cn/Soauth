package com.soauth.server.service;

import com.soauth.core.vo.oauth2.ClientDetails;
import com.soauth.core.vo.oauth2.Oauth2Code;

/**
 * Created by zhoujie on 2017/9/25.
 */
public interface Oauth2Service {

    /**
     * 根据client id加载 client信息
     * @param clientId
     * @return
     */
    ClientDetails loadClientDetails(String clientId);

    /**
     *  根据clientid 和 code 加载Code信息
     * @param code
     * @param clientDetails
     * @return
     */
    Oauth2Code  loadOauthCode(String code, ClientDetails clientDetails);

    /**
     *  保存code
     * @param code
     * @return
     */
    int saveOauthcode (Oauth2Code code);

}
