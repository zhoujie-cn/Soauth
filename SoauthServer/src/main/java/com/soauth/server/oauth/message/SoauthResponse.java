package com.soauth.server.oauth.message;

import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by zhoujie on 2017/9/29.
 *
 *  response client_id , redirect_uri, scope, client_secret, GrantType, code
 */
public abstract  class SoauthResponse {

     protected OAuthRequest oauthRequest;


     public SoauthResponse(OAuthRequest oAuthRequest){ this.oauthRequest=oAuthRequest;}

    /**
     *  验证完毕后返回成功的消息
     */
    public  static  class SuccessResponse{

        /**
         *   return ruccess json message
         */
         public static  void oauthjsonResponse(HttpServletResponse httpServletResponse, OAuthResponse oAuthResponse){
                final  String  locationUri= oAuthResponse.getLocationUri();
                final  Map<String,String> headers =oAuthResponse.getHeaders();
                       headers.forEach((key,value)->{
                             httpServletResponse.addHeader(key, headers.get(key));
                       });

                httpServletResponse.setStatus(oAuthResponse.getResponseStatus());
                httpServletResponse.setContentType(OAuth.ContentType.JSON);
                 // CORS setting
                 httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");

                try {
                 httpServletResponse.sendRedirect(locationUri);
                    final PrintWriter out=httpServletResponse.getWriter();
                    out.print(oAuthResponse.getBody());
                    out.flush();
                } catch (IOException e) {
                 e.printStackTrace();
                }

         }

        /**
         * return Query
         */
         public  static  void oauthQueryResponse( HttpServletResponse servletResponse, OAuthResponse oAuthResponse){

             final String locationUri = oAuthResponse.getLocationUri();

             final  Map<String,String> headers =oAuthResponse.getHeaders();
             headers.forEach((key,value)->{
                 servletResponse.addHeader(key, headers.get(key));
             });
             servletResponse.setStatus(oAuthResponse.getResponseStatus());

             try {
                 servletResponse.sendRedirect(locationUri);
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }

     }

    protected  OAuthResponse invalidCodeResponse(String code) throws OAuthSystemException {
        return OAuthResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                .setError(OAuthError.TokenResponse.INVALID_GRANT)
                .setErrorDescription("Invalid code '" + code + "'")
                .buildJSONMessage();
    }

    protected OAuthResponse invalidClientErrorResponse() throws OAuthSystemException {
        return OAuthResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                .setErrorDescription("Invalid client_id '" + oauthRequest.getClientId() + "'")
                .buildJSONMessage();
    }

    protected OAuthResponse invalidRedirectUriResponse() throws OAuthSystemException {
        return OAuthResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                .setError(OAuthError.CodeResponse.INVALID_REQUEST)
                .setErrorDescription("Invalid redirect_uri '" + oauthRequest.getRedirectURI() + "'")
                .buildJSONMessage();
    }

    protected OAuthResponse invalidScopeResponse() throws OAuthSystemException {
        return OAuthResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                .setError(OAuthError.CodeResponse.INVALID_SCOPE)
                .setErrorDescription("Invalid scope '" + oauthRequest.getScopes() + "'")
                .buildJSONMessage();
    }

    protected OAuthResponse invalidClientSecretResponse() throws OAuthSystemException {
        return OAuthResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                .setError(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT)
                .setErrorDescription("Invalid client_secret by client_id '" + oauthRequest.getClientId() + "'")
                .buildJSONMessage();
    }


    protected OAuthResponse invalidGrantTypeResponse(String grantType) throws OAuthSystemException {
        return OAuthResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                .setError(OAuthError.TokenResponse.INVALID_GRANT)
                .setErrorDescription("Invalid grant_type '" + grantType + "'")
                .buildJSONMessage();
    }


}
