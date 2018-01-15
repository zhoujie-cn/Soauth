package com.soauth.server.openid.connect.authentications.validator;

import com.soauth.core.vo.oauth2.ClientDetails;
import com.soauth.server.oauth.SoauthTokenRequest;
import com.soauth.server.oauth.validator.client.BaseClientValidator;
import com.soauth.server.oauth.validator.client.BaseTokenClientValidator;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author zhoujie
 * @date 2017/11/2
 *
 * 如果Client发起的是一个OpenidConnect请求那么执行此验证器
 */
public  class AuthnHandlerValidator extends BaseTokenClientValidator{
    private  static final Logger log= LoggerFactory.getLogger(AuthnHandlerValidator.class);

    protected SoauthTokenRequest TokenRequest;

    public AuthnHandlerValidator(SoauthTokenRequest TokenRequest) {
        super(TokenRequest);
        this.TokenRequest=TokenRequest;
    }

    @Override
    protected OAuthResponse validateSelf(ClientDetails clientDetails) throws OAuthSystemException {

        String grantType=grentType();

        if(!clientDetails.getGrantTypes().contains(grantType)){
            log.debug("Invalid grant_type '{}', client_id = '{}'", grantType, clientDetails.getClientId());
            return  invalidGrantTypeResponse(grantType);
        }
        //validate clientSecret
        final  String clientSecret= authRequest.getClientSecret();
        if(clientSecret==null || !clientSecret.equals(clientDetails.getClientSecret())){
            log.info("error clientSecret{} clientid{}",clientSecret,clientDetails.getClientId());
            return  invalidClientSecretResponse();
        }

        //validate redirect_uri
        final String redirectURI = authRequest.getRedirectURI();
        if (redirectURI == null || !redirectURI.equals(clientDetails.getRedirectUri())) {
            log.info("error  redirect_uri '{}', client_id = '{}'", redirectURI, clientDetails.getClientId());
            return invalidRedirectUriResponse();
        }


        return null;
    }


}
