package com.soauth.server.oauth.validator.client;

import com.soauth.core.vo.oauth2.ClientDetails;
import com.soauth.core.vo.oauth2.Oauth2Code;
import com.soauth.server.oauth.SoauthTokenRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhoujie on 2017/9/29.
 */
public class AuthzcodeClientValidator extends ClientValidator {

    private  static final Logger log= LoggerFactory.getLogger(AuthzcodeClientValidator.class);
    public AuthzcodeClientValidator(SoauthTokenRequest oAuthRequest) {
        super(oAuthRequest);
    }

    /**
     * /oauth/token?client_id=unity-client&client_secret=unity&grant_type=authorization_code&code=zLl170&redirect_uri=redirect_uri
     * @param clientDetails
     * @return
     * @throws OAuthSystemException
     */
    @Override
    protected OAuthResponse validateSelf(ClientDetails clientDetails) throws OAuthSystemException {

        final String grant_type=grant_type();
        if(!clientDetails.grantTypes().contains(grant_type)){
            log.info("Invalid grant_type '{}', client_id = '{}'", grant_type, clientDetails.clientId());
            return  invalidGrantTypeResponse(grant_type);
        }

        //validate clientSecret
        final  String clientSecret= authRequest.getClientSecret();
        if(clientSecret==null || !clientSecret.equals(clientDetails.clientSecret())){
            log.info("error clientSecret{} clientid{}",clientSecret,clientDetails.clientId());
            return  invalidClientSecretResponse();
        }

        //validate redirect_uri
        final String redirectURI = oauthRequest.getRedirectURI();
        if (redirectURI == null || !redirectURI.equals(clientDetails.redirectUri())) {
            log.info("error  redirect_uri '{}', client_id = '{}'", redirectURI, clientDetails.clientId());
            return invalidRedirectUriResponse();
        }

        // validate code
        String code=code();
        Oauth2Code oauth2Code=oauth2Service.loadOauthCode(code,clientDetails);
         if(oauth2Code==null){
              log.info("not code:{}",code);
              return  invalidCodeResponse(code);
         }
        return null;
    }

    public String code( ){  return ((SoauthTokenRequest) oauthRequest).getCode(); }
}
