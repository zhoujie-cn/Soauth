package com.soauth.server.oauth.token;

import com.soauth.core.vo.oauth2.AccessToken;
import com.soauth.server.oauth.SoauthTokenRequest;
import com.soauth.server.oauth.message.BaseSoauthResponse;
import com.soauth.server.oauth.validator.client.AuthzcodeClientValidator;
import com.soauth.server.oauth.validator.client.BaseClientValidator;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author zhoujie
 * @date 2017/10/10
 */
public class AuthorizationCodeTokenImpl extends BaseOauth2GrantsHandler {

    private  final  static Logger log= LoggerFactory.getLogger(AuthorizationCodeTokenImpl.class);
    @Override
    public boolean support(SoauthTokenRequest tokenRequest) throws OAuthProblemException {

        final  String grantType=tokenRequest.getGrantType();
        return GrantType.AUTHORIZATION_CODE.toString().equalsIgnoreCase(grantType);
    }

    @Override
    protected BaseClientValidator getValidator() {
        return new AuthzcodeClientValidator(tokenRequest);
    }

    /**
     *   ÏìÓ¦AccessToken¡¡²¢É¾³ýCodeÂë
     * @throws OAuthProblemException
     * @throws OAuthSystemException
     */
    @Override
    protected void afterValidate() throws OAuthProblemException, OAuthSystemException {
        responseNewToken();

        removeCode();
    }

    private void  responseNewToken() throws OAuthSystemException {
        AccessToken accessToken=oauth2Service.authorizationCodeAccessToken(getclientDetails(),tokenRequest.getCode(),tokenRequest);

        final OAuthResponse oAuthResponse=createAccessToken(accessToken,false);
        log.debug("authorization_code response{}",oAuthResponse);
        BaseSoauthResponse.SuccessResponse.oauthjsonResponse(response,oAuthResponse,false);
    }

    private  void removeCode(){
        String code=tokenRequest.getCode();
        boolean status= oauth2Service.removeCode(code,getclientDetails().getClientId());
        if(status){
            log.debug("delete code{} status{}", code, status);
        }else {
            log.error("delete code{} status{}", code, status);
        }
    }
}
