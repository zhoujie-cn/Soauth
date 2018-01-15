package com.soauth.server.oauth.authorize;

import com.soauth.core.vo.oauth2.AbstractOIDC;
import com.soauth.core.vo.oauth2.ClientDetails;
import com.soauth.server.oauth.SoauthAuthRequest;
import com.soauth.server.oauth.message.BaseSoauthResponse;
import com.soauth.server.oauth.validator.authzgrant.BaseAuthzHandlerValidator;
import com.soauth.server.oauth.validator.client.BaseClientValidator;
import com.soauth.server.oauth.validator.client.CodeClientValidator;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * @author zhoujie
 * @date 2017/9/22
 *
 * Http Handle response_type=token
 */
public class AuthzCodeHandler extends BaseAuthzHandlerValidator {
    private static final Logger log = LoggerFactory.getLogger(AuthzCodeHandler.class);

    protected  String appType;
    public AuthzCodeHandler(SoauthAuthRequest soauthAuthRequest, HttpServletResponse response){
         super(soauthAuthRequest,response);

       this.appType=  soauthAuthRequest.getParam(AbstractOIDC.OIDC_DISPLAY);
    }

    @Override
    protected BaseClientValidator getValidator() {
        return new CodeClientValidator(soauthAuthRequest);
    }

    /**
     *  response code
     * @throws OAuthSystemException
     * @throws IOException
     */
    @Override
    protected void callbackResponse() throws OAuthSystemException, IOException {

         log.info("code callbackResponse...");
        ClientDetails clientDetails =getclientDetails();

        String code=oauth2Service.authCode(clientDetails);

        final OAuthResponse oAuthResponse= OAuthASResponse
                                               .authorizationResponse(soauthAuthRequest.request(),HttpServletResponse.SC_OK)
                                               .location(clientDetails.getRedirectUri())
                                                .setCode(code)
                                                .setParam(AbstractOIDC.OIDC_DISPLAY,appType)
                                                .buildQueryMessage();
        log.info("code={} Response{}",code,oAuthResponse);

        BaseSoauthResponse.SuccessResponse.oauthQueryResponse(response,oAuthResponse);
    }


}
