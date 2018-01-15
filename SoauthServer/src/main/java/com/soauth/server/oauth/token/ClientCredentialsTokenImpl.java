package com.soauth.server.oauth.token;

import com.soauth.core.vo.oauth2.AccessToken;
import com.soauth.server.oauth.SoauthTokenRequest;
import com.soauth.server.oauth.message.BaseSoauthResponse;
import com.soauth.server.oauth.validator.client.ClientCredentialsValidator;
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
 * @date 2017/10/11
 */
public class ClientCredentialsTokenImpl extends BaseOauth2GrantsHandler {
    private final static Logger log= LoggerFactory.getLogger(ClientCredentialsTokenImpl.class);
    @Override
    public boolean support(SoauthTokenRequest tokenRequest) throws OAuthProblemException {
       final  String grantType=tokenRequest.getGrantType();

        return GrantType.CLIENT_CREDENTIALS.toString().equalsIgnoreCase(grantType);
    }

    @Override
    protected BaseClientValidator getValidator() {
        return new ClientCredentialsValidator(tokenRequest);
    }

    @Override
    protected void afterValidate() throws OAuthProblemException, OAuthSystemException {
        AccessToken accessToken=oauth2Service.clientCredentialsAccessToken(getclientDetails(),tokenRequest.getScopes());

        final OAuthResponse response= createAccessToken(accessToken,false);
        log.debug("clientCredent Response Token",response);
        BaseSoauthResponse.SuccessResponse.oauthjsonResponse(this.response,response,false);
    }
}
