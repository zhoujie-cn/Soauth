package com.soauth.server.oauth.validator.client;

import com.soauth.core.vo.oauth2.ClientDetails;
import com.soauth.server.oauth.SoauthAuthRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 *
 * @author zhoujie
 * @date 2017/10/12
 *
 * grant_type="implicit"   -> response_type="token"
 */
public class TokenClientValidator extends BaseClientValidator {
   private final  static Logger log= LoggerFactory.getLogger(TokenClientValidator.class);

    public TokenClientValidator(SoauthAuthRequest oAuthRequest) {
        super(oAuthRequest);
    }

    @Override
    protected OAuthResponse validateSelf(ClientDetails clientDetails) throws OAuthSystemException {

        //verify clientSecret
        final String clientSecret=oauthRequest.getClientSecret();
        if(clientSecret == null ||!clientSecret.equals(clientDetails.getClientSecret())){
            log.debug("clientSecret ÎÞÐ§{}",clientSecret);
            return  invalidClientSecretResponse();
        }

        //verify  redirect_uri
        final String redirecturi=oauthRequest.getRedirectURI();
        if(redirecturi==null || !redirecturi.equals(clientDetails.getRedirectUri())){
            return invalidRedirectUriResponse();
        }

        //verify  scope
        final Set<String> scopes = oauthRequest.getScopes();
        if (scopes.isEmpty() || verifyScopes(scopes, clientDetails)) {
            return invalidScopeResponse();
        }
        return null;
    }
}
