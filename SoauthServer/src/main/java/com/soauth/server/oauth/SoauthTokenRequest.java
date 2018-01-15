package com.soauth.server.oauth;

import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 *
 * @author zhoujie
 * @date 2017/9/29
 * exts OauthTokenRequest
 */
public class SoauthTokenRequest extends OAuthTokenRequest {

    /**
     * Create an OAuth Token request from a given HttpSerlvetRequest
     *
     * @param request the httpservletrequest that is validated and transformed into the OAuth Token Request
     * @throws OAuthSystemException  if an unexpected exception was thrown
     * @throws OAuthProblemException if the request was not a valid Token request this exception is thrown.
     */
    public SoauthTokenRequest(HttpServletRequest request) throws OAuthSystemException, OAuthProblemException {
        super(request);
    }


    public HttpServletRequest request() {
        return this.request;
    }

    public boolean isOpenid(){

        return this.getScopes().contains("openid");
    }
}
