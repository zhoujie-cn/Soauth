package com.soauth.server.oauth.validator.client;

import com.soauth.core.vo.oauth2.ClientDetails;
import com.soauth.server.oauth.SoauthTokenRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 *
 * @author zhoujie
 * @date 2017/10/11
 *
 * validate grantType, clientSecret, scopes
 */
public class ClientCredentialsValidator extends BaseTokenClientValidator {

    private final static Logger log= LoggerFactory.getLogger(ClientCredentialsValidator.class);

    public ClientCredentialsValidator(SoauthTokenRequest oAuthRequest) {
        super(oAuthRequest);
    }

    @Override
    protected OAuthResponse validateSelf(ClientDetails clientDetails) throws OAuthSystemException {
       final  String grantType=grentType();
       // verify grantType
       if(!clientDetails.getGrantTypes().contains(grantType)){
            log.debug("ClientCredent grantType{} 无效",grantType);
            invalidGrantTypeResponse(grantType);
       }
       // verify clientSecret
        final String clientSecret=oauthRequest.getClientSecret();
       if(clientSecret == null ||!clientSecret.equals(clientDetails.getClientSecret())){
           log.debug("clientSecret 无效{}",clientSecret);
           return  invalidClientSecretResponse();
       }
       //verify  scopes
       final Set<String>scopes=oauthRequest.getScopes();
       if(scopes.isEmpty() || verifyScopes(scopes,clientDetails)){
           log.info("没有权限");
           return invalidScopeResponse();
       }
        return null;
    }
}
