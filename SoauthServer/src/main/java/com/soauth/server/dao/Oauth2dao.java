package com.soauth.server.dao;

import com.soauth.core.vo.oauth2.ClientDetails;
import com.soauth.core.vo.oauth2.Oauth2Code;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author zhoujie
 * @date 2017/9/26
 *
 */
public interface Oauth2dao {
    /**
     *  ¸ù¾Ý clientid
     * @param clientid
     * @param code
     * @return
     */
     Oauth2Code findOauth2Code(@Param(value ="clientid") String clientid, @Param(value = "code") String code);


    ClientDetails loadClientDetails(@Param("clientid") String clientId);

    /**
     * É¾³ýcode
     * @param code
     * @param clientid
     * @return
     */
    int  removeCode( @Param(value = "code") String code,@Param(value ="clientid") String clientid);
}
