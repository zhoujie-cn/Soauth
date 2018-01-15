package com.soauth.server.oauth.validator.client;

import com.soauth.core.vo.oauth2.ClientDetails;
import com.soauth.core.vo.oauth2.Oauth2Code;
import com.soauth.server.oauth.SoauthTokenRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;

/**
 *
 * @author zhoujie
 * @date 2017/9/29
 *
 * validate grantType, redirect_uri, code
 */
public class AuthzcodeClientValidator extends BaseTokenClientValidator{

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
        log.info("code  validateSelf{}"+clientDetails);
        final String grant_type=grentType();

        clientDetails.setGrantTypes(new HashSet<>(Arrays.asList(clientDetails.getGrantType().split(","))));
        if(!clientDetails.getGrantTypes().contains(grant_type)){
            log.info("Invalid grant_type '{}', client_id = '{}'", grant_type, clientDetails.getClientId());
            return  invalidGrantTypeResponse(grant_type);
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

        // validate code
        String code=code();
        Oauth2Code oauth2Code=oauth2Service.loadOauthCode(code,clientDetails.getClientId());

         if(oauth2Code.code()==null){
              log.info("not code:{}",code);
              return  invalidCodeResponse(code);
         }
         log.info("success  validateSelf");
        return null;
    }

    public String code( ){  return (tokenRequest).getCode(); }
}
