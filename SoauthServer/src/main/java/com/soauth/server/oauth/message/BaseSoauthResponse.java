package com.soauth.server.oauth.message;

import com.soauth.server.oauth.validator.client.BaseClientValidator;
import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 *
 * @author zhoujie
 * @date 2017/9/29
 *
 *  response client_id , redirect_uri, scope, client_secret, GrantType, code
 */
public abstract  class BaseSoauthResponse {
    private  static final Logger log= LoggerFactory.getLogger(BaseSoauthResponse.class);

     protected OAuthRequest oauthRequest;

     public BaseSoauthResponse(OAuthRequest oAuthRequest){ this.oauthRequest=oAuthRequest;}

    /**
     *  验证完毕后返回的消息
     */
    public  static  class SuccessResponse{

        /**
         *
         * @param httpServletResponse
         * @param oAuthResponse
         * @param whetherRedirect false 不进行重定向, true,进行重定向  此参数用于是否确定启动重定向. 在使用Httpclient工具时,如果启用回调地址那么restTemplate.getForObject将获取不到值.
         *
         *
         */
         public static  void oauthjsonResponse(HttpServletResponse httpServletResponse, OAuthResponse oAuthResponse, boolean whetherRedirect){

                final  Map<String,String> headers =oAuthResponse.getHeaders();
                       headers.forEach((key,value)->{
                             httpServletResponse.addHeader(key, headers.get(key));
                       });

                httpServletResponse.setStatus(oAuthResponse.getResponseStatus());
                httpServletResponse.setContentType(OAuth.ContentType.JSON);
                 // CORS setting
                 httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");

                try {
                    if(whetherRedirect) {
                        final String locationUri = oAuthResponse.getLocationUri();
                        httpServletResponse.sendRedirect(locationUri);
                    }

                    final PrintWriter out=httpServletResponse.getWriter();
                    log.info(oAuthResponse.getBody());
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

               log.debug("op 回调rp redirect uri{}",locationUri);
             servletResponse.setHeader("Access-Control-Allow-Origin", "*");
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

    protected OAuthResponse invalidUsernamePwdResponse() throws OAuthSystemException {
        return OAuthResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                .setError(OAuthError.TokenResponse.INVALID_GRANT)
                .setErrorDescription("Bad credentials")
                .buildJSONMessage();
    }


    protected OAuthResponse invalidRefreshTokenResponse(String refreshToken) throws OAuthSystemException {
        return OAuthResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                .setError(OAuthError.TokenResponse.INVALID_GRANT)
                .setErrorDescription("Invalid refresh_token: " + refreshToken)
                .buildJSONMessage();
    }

    protected  OAuthResponse invalidStateResponse() throws OAuthSystemException {

        return OAuthResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                .setError(OAuthError.CodeResponse.INVALID_REQUEST)
                .setErrorDescription("Parameter 'state'  is required")
                .buildJSONMessage();
    }

    /**
     *  授权失败
     * @param e
     * @param request
     * @param response
     */
    public  static void authzwrongResponse(OAuthProblemException e, HttpServletRequest request, HttpServletResponse response){

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
        }catch (OAuthSystemException  | IOException es) {
            es.printStackTrace();
        }

    }

    /**
     * 错误响应
     * document:
     * http://openid.net/specs/openid-connect-core-1_0.html#AuthResponse
     * @param errmsg
     * @param request
     * @param response
     */
    public  static void authnwrongResponse(String errmsg, HttpServletRequest request, HttpServletResponse response){

        final OAuthResponse oAuthResponse;
        try {
            oAuthResponse = OAuthASResponse
                    .errorResponse(HttpServletResponse.SC_FOUND)
                    .location(request.getContextPath())
                    .setError(errmsg)
                    .setState(request.getParameter("state"))
                    .buildQueryMessage();
            response.setContentType(OAuth.ContentType.URL_ENCODED);
            response.sendRedirect(oAuthResponse.getLocationUri());

            final PrintWriter out = response.getWriter();
            out.print(oAuthResponse.getBody());
            out.flush();
        }catch (OAuthSystemException  | IOException es) {
            es.printStackTrace();
        }

    }



}
