package com.soauth.server.oauth.token;

import com.google.common.collect.Lists;
import com.soauth.server.oauth.SoauthTokenRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author zhoujie
 * @date 2017/10/8
 *
 * 匹配Handler
 */
public class TokenDispatcher {
    private static final Logger log = LoggerFactory.getLogger(TokenDispatcher.class);

    /**
     * 装载所有授权类
     */
    private List<SoauthHandler> handlerList= Lists.newArrayList();

    private SoauthTokenRequest tokenRequest;

    private HttpServletResponse response;

    public TokenDispatcher(HttpServletResponse response, SoauthTokenRequest tokenRequest){
        this.tokenRequest=tokenRequest;
        this.response=response;
        initialAuthorizations();
    }

    /**
     * 初始化所有授权类
     */
    private void initialAuthorizations(){
            handlerList.add(new AuthorizationCodeTokenImpl());
            handlerList.add(new RefreshTokenImpl());
            handlerList.add(new ClientCredentialsTokenImpl());
    }

    public  void doDispatch() throws OAuthProblemException, OAuthSystemException {
        for (SoauthHandler handler: handlerList) {
            if(handler.support(tokenRequest)){
                handler.handle(tokenRequest,response);
                return;
            }
        }
        throw new IllegalStateException("找不到 'OAuthTokenHandler' ");
    }
}
