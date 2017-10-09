package com.soauth.server.oauth.authorize;

import com.soauth.server.oauth.SoauthAuthRequest;
import com.soauth.server.oauth.validator.authzGrant.AuthzHandlerValidator;
import com.soauth.server.oauth.validator.client.ClientValidator;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhoujie on 2017/9/22.
 */
public class AuthzCodeHandler extends AuthzHandlerValidator {

    public AuthzCodeHandler(SoauthAuthRequest soauthAuthRequest, HttpServletResponse response){
         super(soauthAuthRequest,response);
    }


    protected ClientValidator getValidator() {
        return null;
    }


}
