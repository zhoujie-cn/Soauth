package com.soauth.server.dao;

import com.soauth.core.vo.oauth2.AccessToken;
import com.soauth.core.vo.oauth2.Oauth2Code;
import com.soauth.core.vo.oauth2.RefreshToken;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author zhoujie
 * @date 2017/12/6
 */
public interface Tokendao {

    /**
     * 保存accessToken
     * @param accessToken
     * @return
     */
    AccessToken saveAccessToken(AccessToken accessToken);

    /**
     * 保存refreshToken
     * @param refreshToken
     * @return
     */
    RefreshToken saveRefreshToken(RefreshToken refreshToken);

    /**
     *  根据 clientid, code 查询
     * @param clientid
     * @param code
     * @return
     */
    Oauth2Code findOauth2Code(@Param("clientid") String clientid, @Param("code") String code);

    /**
     *  根据clientid , username 查询
     * @param username
     * @param clientid
     * @return
     */
    Oauth2Code findOauth2CodeUserAndClientId( @Param("username") String username, @Param("clientid") String clientid);

    /**
     * 保存code
     * @param code
     * @return
     */
    int saveOauth2Code(Oauth2Code code);

    /**
     *   删除code
     * @param code
     * @return
     */
    int deleteOauth2Code(Oauth2Code code);

}
