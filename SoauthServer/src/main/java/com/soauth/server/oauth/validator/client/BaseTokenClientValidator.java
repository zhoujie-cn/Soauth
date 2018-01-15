package com.soauth.server.oauth.validator.client;

import com.soauth.server.oauth.SoauthTokenRequest;
import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.common.OAuth;

/**
 *
 * @author zhoujie
 * @date 2017/8/29
 */
public abstract class BaseTokenClientValidator  extends  BaseClientValidator{

    SoauthTokenRequest tokenRequest;

    /**
     * ≥ı ºªØ CodeClientValidator TokenClientValidator
     *
     * @param tokenRequest
     */
    public BaseTokenClientValidator(SoauthTokenRequest tokenRequest) {
        super(tokenRequest);

        this.tokenRequest=tokenRequest;
    }


    protected String grentType( ){
        return tokenRequest.getGrantType();
    }
}
