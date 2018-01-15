package com.soauth.server.oauth.token;

import com.soauth.core.vo.oauth2.AccessToken;
import com.soauth.server.oauth.SoauthTokenRequest;
import com.soauth.server.oauth.message.BaseSoauthResponse;
import com.soauth.server.oauth.validator.client.BaseClientValidator;
import com.soauth.server.oauth.validator.client.RefreshTokenClientValidator;
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
 *
 * 更新token 类
 */
public class RefreshTokenImpl extends BaseOauth2GrantsHandler {

     private final static Logger log= LoggerFactory.getLogger(RefreshTokenImpl.class);


    @Override
    public boolean support(SoauthTokenRequest tokenRequest) throws OAuthProblemException {

        final  String grantType=tokenRequest.getGrantType();
        return GrantType.REFRESH_TOKEN.toString().equalsIgnoreCase(grantType);
    }

    @Override
    protected BaseClientValidator getValidator() {
        return new RefreshTokenClientValidator(tokenRequest);
    }

    /**
     *  更新token
     *   request url:
     *   /oauth/token?client_id=mobile-client&client_secret=mobile&grant_type=refresh_token&refresh_token=b36f4978-a172-4aa8-af89-60f58abe3ba1
     *
     * @throws OAuthProblemException
     * @throws OAuthSystemException
     */
    @Override
    protected void afterValidate() throws OAuthProblemException, OAuthSystemException {
              final  String refreshToken= tokenRequest.getRefreshToken();

        AccessToken accessToken = oauth2Service.changeRefreshToken(refreshToken,tokenRequest.getClientId());
        OAuthResponse oAuthResponse=createAccessToken(accessToken,false);

        log.debug("refreshtoken response{}",oAuthResponse);

        BaseSoauthResponse.SuccessResponse.oauthjsonResponse(this.response,oAuthResponse,false);
    }
}
