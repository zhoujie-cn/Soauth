package com.soauth.server.web.controller;

import com.soauth.server.oauth.SoauthAuthRequest;
import com.soauth.server.utils.MessageUtils;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by zhoujie on 2017/9/20.
 *
 * Client 发起授权请求
 */
@Controller
@RequestMapping(value ="oauth2")
public class AuthzControl {

    private static final Logger log = LoggerFactory.getLogger(AuthzControl.class);

    /**
     *验证 authorization_code 的response_type为"code" 还是 "token"
     * @param request
     * @param response
     */
    @RequestMapping("authorize")
    public void authorize(HttpServletRequest request, HttpServletResponse response) {
        try {
            SoauthAuthRequest authRequest = new SoauthAuthRequest(request);

            if (authRequest.isCode()){
                log.info("authorize response_type=code");
            }

        } catch (OAuthSystemException e) {
            e.printStackTrace();

        } catch (OAuthProblemException e) {
            MessageUtils.authzwrongResponse(e,request,response);
        }
    }
}

