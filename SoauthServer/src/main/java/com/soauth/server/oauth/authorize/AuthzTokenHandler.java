package com.soauth.server.oauth.authorize;

import com.soauth.core.vo.oauth2.AccessToken;
import com.soauth.core.vo.oauth2.ClientDetails;
import com.soauth.server.oauth.SoauthAuthRequest;
import com.soauth.server.oauth.message.BaseSoauthResponse;
import com.soauth.server.oauth.validator.authzgrant.BaseAuthzHandlerValidator;
import com.soauth.server.oauth.validator.client.BaseClientValidator;
import com.soauth.server.oauth.validator.client.TokenClientValidator;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author zhoujie
 * @date 2017/10/8
 */
public class AuthzTokenHandler extends BaseAuthzHandlerValidator {
    private static final Logger log = LoggerFactory.getLogger(AuthzTokenHandler.class);

    public AuthzTokenHandler(SoauthAuthRequest soauthAuthRequest, HttpServletResponse response) {
        super(soauthAuthRequest, response);
    }

    @Override
    protected BaseClientValidator getValidator() {
        return new TokenClientValidator(soauthAuthRequest);
    }

    /**
     * response new accesstoken
     * @throws OAuthSystemException
     * @throws IOException
     */
    @Override
    protected void callbackResponse() throws OAuthSystemException, IOException {
               if (checkNewToken()){
                    accessTokenResponse(); //response json
               }else{
                 AccessToken accessToken =oauth2Service.retrievalsAccessToken(getclientDetails(),soauthAuthRequest,false);
                    if(accessToken.tokenExpired()) {
                        expiredTokennews(accessToken, response);
                    }
                    else {
                        // response query
                        accessTokenResponse(accessToken);
                    }
               }
    }

    /**
     *  检查是否可以创建凭证
     * @return
     */
    private boolean  checkNewToken(){
        ClientDetails clientDetails =getclientDetails();
       return clientDetails.isTrusted() ?  userLogin :  userApproved;
    }

    /**
     *   返回token信息 json
     * @throws OAuthSystemException
     */
    private void accessTokenResponse() throws OAuthSystemException {
        AccessToken accessToken = oauth2Service.newAccessToken(getclientDetails(),soauthAuthRequest.getScopes());
        final OAuthResponse oAuthResponse =createAccessToken(accessToken, true);

        BaseSoauthResponse.SuccessResponse.oauthjsonResponse(response,oAuthResponse,false);
    }

    /**
     *   返回token信息 query
     * @param accessToken
     * @throws OAuthSystemException
     */
    private void accessTokenResponse(AccessToken accessToken)throws  OAuthSystemException{
        final OAuthResponse oAuthResponse = createAccessToken(accessToken, true);
        BaseSoauthResponse.SuccessResponse.oauthQueryResponse(response,oAuthResponse);
    }
}
