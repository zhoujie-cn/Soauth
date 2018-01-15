package com.soauth.server.web.authncontroller;

import com.soauth.server.oauth.SoauthTokenRequest;
import com.soauth.server.oauth.token.TokenDispatcher;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author zhoujie
 * @date 2017/12/1
 *
 * ªÒ»°Token
 */

@Controller
@RequestMapping(value ="openid")
public class AuthTokenEndpoint {

   private Logger log= LoggerFactory.getLogger(this.getClass());

   @RequestMapping(value = "token",method = RequestMethod.POST)
   public  void getTokens(HttpServletRequest request, HttpServletResponse response){
       log.debug("Client «Î«ÛToken...");
        SoauthTokenRequest tokenRequest;
        try {
           tokenRequest = new SoauthTokenRequest(request);

            TokenDispatcher dispatcher = new TokenDispatcher(response,tokenRequest);

            dispatcher.doDispatch();

        } catch (OAuthSystemException e) {
            e.printStackTrace();
        } catch (OAuthProblemException e) {
            e.printStackTrace();
        }

   }
}
