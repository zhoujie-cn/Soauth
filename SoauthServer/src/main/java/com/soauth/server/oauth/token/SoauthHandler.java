package com.soauth.server.oauth.token;
import com.soauth.server.oauth.SoauthTokenRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author zhoujie
 * @date 2017/10/10
 */
public interface SoauthHandler {

    /**
     * 判断当前请求是否有类型支持
     * @param tokenRequest
     * @return
     * @throws OAuthProblemException
     */
    boolean support(SoauthTokenRequest tokenRequest) throws OAuthProblemException;

    /**
     *  子类初始化 request, response并执行验证
     * @param tokenRequest
     * @param response
     * @throws OAuthProblemException
     * @throws OAuthSystemException
     */
    void handle(SoauthTokenRequest tokenRequest, HttpServletResponse response) throws OAuthProblemException, OAuthSystemException;
}
