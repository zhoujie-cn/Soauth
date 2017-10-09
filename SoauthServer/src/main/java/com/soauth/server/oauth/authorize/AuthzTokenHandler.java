package com.soauth.server.oauth.authorize;

import com.soauth.server.oauth.SoauthAuthRequest;
import com.soauth.server.oauth.validator.authzGrant.AuthzHandlerValidator;
import com.soauth.server.oauth.validator.client.ClientValidator;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhoujie on 2017/10/8.
 */
public class AuthzTokenHandler extends AuthzHandlerValidator {

    public AuthzTokenHandler(SoauthAuthRequest soauthAuthRequest, HttpServletResponse response) {
        super(soauthAuthRequest, response);
    }


    protected ClientValidator getValidator() {
        return null;
    }
}
