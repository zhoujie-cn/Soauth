package com.soauth.server.utils;

import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by zhoujie on 2017/9/22.
 *
 */
public class MessageUtils {

     public  static void authzwrongResponse(OAuthProblemException e, HttpServletRequest request,HttpServletResponse response){

          final OAuthResponse oAuthResponse;
          try {
               oAuthResponse = OAuthASResponse
                       .errorResponse(HttpServletResponse.SC_FOUND)
                       .location("http://www.baidu.com")
                       .error(e)
                       .setState(request.getParameter("state"))
                       .buildQueryMessage();
               response.setContentType(OAuth.ContentType.URL_ENCODED);
                    response.sendRedirect(oAuthResponse.getLocationUri());

               final PrintWriter out = response.getWriter();
               out.print(oAuthResponse.getBody());
               out.flush();
          } catch (OAuthSystemException  | IOException es) {
               es.printStackTrace();
          }

     }
}
