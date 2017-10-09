package com.soauth.server.oauth.validator.client;

import com.soauth.core.vo.oauth2.ClientDetails;
import com.soauth.server.oauth.SoauthTokenRequest;
import com.soauth.server.oauth.message.SoauthResponse;
import com.soauth.server.service.Oauth2Service;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhoujie on 2017/9/23.
 *
 * 验证客户端参数的公有类
 * AbstractClientDetailsValidator
 */
public abstract class ClientValidator extends SoauthResponse {
     private  static final Logger log= LoggerFactory.getLogger(ClientValidator.class);

     @Autowired
     protected Oauth2Service oauth2Service;

     protected SoauthTokenRequest authRequest;

     private  ClientDetails clientDetails;

     public ClientValidator(SoauthTokenRequest  oAuthRequest){
         super(oAuthRequest);
         authRequest=oAuthRequest;

     }

    protected String grant_type(){ return authRequest.getGrantType(); }


    protected  ClientDetails clientDetails( ){
        return   clientDetails == null ? (clientDetails=oauth2Service.loadClientDetails(authRequest.getClientId())): clientDetails ;
    }

    public  final OAuthResponse validate() throws OAuthSystemException{
         ClientDetails clientDetails = clientDetails();
        return clientDetails ==null ? invalidClientErrorResponse(): validateSelf(clientDetails);
    }

    protected abstract OAuthResponse validateSelf(ClientDetails clientDetails) throws OAuthSystemException;

}
