package com.soauth.server.service;

import com.soauth.core.vo.oauth2.AccessToken;
import com.soauth.core.vo.oauth2.ClientDetails;
import com.soauth.core.vo.oauth2.Oauth2Code;
import com.soauth.server.oauth.SoauthAuthRequest;
import com.soauth.server.oauth.SoauthTokenRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import java.util.Set;

/**
 *
 * @author zhoujie
 * @date 2017/9/25
 */
public interface Oauth2Service {

    /**
     * 根据client id加载 client信息
     * @param clientId
     * @return
     */
    ClientDetails loadClientDetails(String clientId);

    /**
     *生成Code
     * @param clientDetails
     * @return
     * @throws OAuthSystemException
     */
    String authCode(ClientDetails clientDetails) throws  OAuthSystemException;

    /**
     *  检索token
     * @param clientDetails
     * @param
     * @param includeRefreshToken
     * @return
     * @throws OAuthSystemException
     */
    AccessToken retrievalsAccessToken(ClientDetails clientDetails, SoauthAuthRequest request, boolean includeRefreshToken) throws OAuthSystemException;

    AccessToken newAccessToken(ClientDetails clientDetails, Set<String> scopes) throws OAuthSystemException;
    /**
     *  根据clientid 和 code 加载Code信息
     * @param code
     * @param  clientid
     * @return
     */
    Oauth2Code  loadOauthCode(String code, String clientid);

    /**
     *  保存code
     * @param code
     * @return
     */
    int saveOauthcode (Oauth2Code code);

    /**
     *
     * @param code
     * @param clientid
     * @return
     */
   boolean removeCode(String code, String  clientid);


    /**
     * grant_type=code
     * @param clientDetails
     * @param code
     * @return
     * @throws OAuthSystemException
     */
    AccessToken authorizationCodeAccessToken(ClientDetails clientDetails, String code, SoauthTokenRequest tokenRequest) throws OAuthSystemException;

    /**
     * grant_type=clientCredent
     * @param clientDetails
     * @param scopes
     * @return
     * @throws OAuthSystemException
     */
    AccessToken clientCredentialsAccessToken(ClientDetails clientDetails, Set<String> scopes) throws  OAuthSystemException;

    /**
     * grant_type=passwordCredent
     * @param clientDetails
     * @param scopes
     * @param username
     * @return
     * @throws OAuthSystemException
     */
    AccessToken passwordAccessToken(ClientDetails clientDetails, Set<String> scopes, String username) throws OAuthSystemException;

    /**
     *loadAccessTokenByRefreshToken
     * @param refeshToken
     * @param clientId
     * @return
     */
    AccessToken findrefreshToken(String refeshToken, String clientId);

    /**
     * changeAccessTokenByRefreshToken
     * @param refreshToken
     * @param clientId
     * @return
     * @throws OAuthSystemException
     */
    AccessToken changeRefreshToken(String refreshToken, String clientId) throws OAuthSystemException;

}
