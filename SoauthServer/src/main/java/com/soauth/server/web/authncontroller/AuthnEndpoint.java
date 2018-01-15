package com.soauth.server.web.authncontroller;
import com.soauth.core.model.UserInfo;
import com.soauth.core.shiro.utils.ShiroUtils;
import com.soauth.core.vo.oauth2.AbstractOIDC;
import com.soauth.server.oauth.SoauthAuthRequest;
import com.soauth.server.oauth.authorize.AuthzCodeHandler;
import com.soauth.server.oauth.authorize.AuthzTokenHandler;
import com.soauth.server.oauth.message.BaseSoauthResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author zhoujie
 * @date 2017/11/24
 */

@Controller
@RequestMapping("openid")
public class AuthnEndpoint {
    private static  final Logger log = LoggerFactory.getLogger(AuthnEndpoint.class);



    @RequestMapping(value = "signin", method =RequestMethod.GET)
    public String signin(){
        return "oauth_signin/signin";
    }

    @RequestMapping(value = "signin", method = RequestMethod.POST)
    public void signin(HttpServletRequest request, HttpServletResponse response, @RequestBody UserInfo userInfo){
        log.debug("signin");
        ShiroUtils.login(userInfo,false);
    }

    /**
     * 接收client请求
     *验证 authorization_code 的response_type为"code" 还是 "token"
     * 验证Scorce
     * @param request
     * @param response
     */

    @CrossOrigin
    @RequestMapping(value = "authorize",method = RequestMethod.GET)
    public void authorize(HttpServletRequest request, HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        try {
            SoauthAuthRequest authRequest = new SoauthAuthRequest(request);

             if (authRequest.isCode()){
                AuthzCodeHandler codeHandler= new AuthzCodeHandler(authRequest,response);
                log.debug("authorize response_type=code");
                codeHandler.verify();

            }else if (authRequest.isToken()){

                log.debug("authorize response_type=token");
                AuthzTokenHandler tokenHandler= new AuthzTokenHandler(authRequest,response);
                tokenHandler.verify();
            }

            invalidReuqestType(authRequest,response);

        } catch (OAuthSystemException e) {
            e.printStackTrace();

        } catch (OAuthProblemException e) {
            BaseSoauthResponse.authzwrongResponse(e,request,response);
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *  参数'response_type'不是token 也不是code 请求无效
     * @param authRequest
     * @param response
     * @throws OAuthSystemException
     */
    private void invalidReuqestType(SoauthAuthRequest authRequest, HttpServletResponse response)throws OAuthSystemException{
        final String responsetype=authRequest.getResponseType();
        log.debug("参数response_type无效");

        final OAuthResponse oAuthResponse=OAuthResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                .setError(OAuthError.CodeResponse.UNAUTHORIZED_CLIENT)
                .setErrorDescription("invalid responseType" + responsetype )
                .buildJSONMessage();
        BaseSoauthResponse.SuccessResponse.oauthjsonResponse(response,oAuthResponse,true);
    }

}
