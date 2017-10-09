package com.soauth.server.oauth;

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ResponseType;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhoujie on 2017/9/21.
 * 封装oltu AthzRequest
 */
public class SoauthAuthRequest extends OAuthAuthzRequest {

    /**
     *
     * @param request 将httpRequest, 转换为 OauthzRequest
     */
    public  SoauthAuthRequest(HttpServletRequest request) throws OAuthSystemException, OAuthProblemException {
        super(request);
    }


    public boolean isCode() {
        return ResponseType.CODE.name().equalsIgnoreCase(this.getResponseType());
    }

    public boolean isToken() {
        return ResponseType.TOKEN.name().equalsIgnoreCase(this.getResponseType());
    }

    public HttpServletRequest request() {
        return this.request;
    }
}
