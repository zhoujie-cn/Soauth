package com.soauth.server.service.impl;

import com.soauth.core.vo.oauth2.AccessToken;
import com.soauth.core.vo.oauth2.ClientDetails;
import com.soauth.core.vo.oauth2.Oauth2Code;
import com.soauth.server.dao.Oauth2dao;
import com.soauth.server.oauth.SoauthAuthRequest;
import com.soauth.server.oauth.SoauthTokenRequest;
import com.soauth.server.service.Oauth2Service;
import com.soauth.server.service.business.AuthDeviceCode;
import com.soauth.server.service.business.AuthzCodeAccessToken;
import com.soauth.server.service.business.ImplicitAccessToken;
import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 *
 * @author zhoujie
 * @date 2017/9/26
 * 负责对Oauth2模式提供抽象实现
 */

@Service()
public class Oauth2ServiceImpl implements Oauth2Service {

 private  Logger logger= LoggerFactory.getLogger(Oauth2ServiceImpl.class);

    @Autowired
    Oauth2dao oauth2dao;


    @Override
    public ClientDetails loadClientDetails(String clientId) {
       ClientDetails clientDetails= oauth2dao.loadClientDetails(clientId);
       return clientDetails;
    }

    @Override
    public String authCode(ClientDetails clientDetails) throws OAuthSystemException {
        AuthDeviceCode deviceCode = new AuthDeviceCode(clientDetails);
        return deviceCode.createAndSaveCode();
    }

    @Override
    public AccessToken retrievalsAccessToken(ClientDetails clientDetails, SoauthAuthRequest request, boolean includeRefreshToken) throws OAuthSystemException {
        ImplicitAccessToken implicitAccessToken =  new ImplicitAccessToken();
        return  implicitAccessToken.createNewAccessToken(clientDetails, request, includeRefreshToken);
    }

    @Override
    public AccessToken newAccessToken(ClientDetails clientDetails, Set<String> scopes) throws OAuthSystemException {
        return null;
    }

    @Override
    public Oauth2Code loadOauthCode(String code, String clientid) {
      return   oauth2dao.findOauth2Code(clientid,code);

    }

    @Override
    public int saveOauthcode(Oauth2Code code) {
        return 0;
    }


    @Override
    public boolean removeCode(String code, String  clientid) {
        final int rows=oauth2dao.removeCode(code,clientid);
        logger.info("removeCode rows......{}",rows);
        return rows>0;
    }



    @Override
    public AccessToken authorizationCodeAccessToken(ClientDetails clientDetails, String code, SoauthTokenRequest tokenRequest) throws OAuthSystemException {
        AuthzCodeAccessToken  accessToken = new AuthzCodeAccessToken();

        return  accessToken.createNewAccessToken(clientDetails,code,tokenRequest);

    }

    @Override
    public AccessToken clientCredentialsAccessToken(ClientDetails clientDetails, Set<String> scopes) throws OAuthSystemException {
        return null;
    }

    @Override
    public AccessToken passwordAccessToken(ClientDetails clientDetails, Set<String> scopes, String username) throws OAuthSystemException {
        return null;
    }

    @Override
    public AccessToken findrefreshToken(String refeshToken, String clientId) {
        return null;
    }

    @Override
    public AccessToken changeRefreshToken(String refreshToken, String clientId) throws OAuthSystemException {
        return null;
    }
}
