package com.soauth.server.oauth.message;

import com.soauth.core.utils.SpringBeanUtils;
import com.soauth.core.vo.oauth2.AbstractOIDC;
import com.soauth.core.vo.oauth2.AccessToken;
import com.soauth.core.vo.oauth2.ClientDetails;
import com.soauth.core.vo.oauth2.RefreshToken;
import com.soauth.server.oauth.SoauthAuthRequest;
import com.soauth.server.service.Oauth2Service;
import org.apache.commons.lang.StringUtils;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author zhoujie
 * @date 2017/10/8
 *
 * 返回token信息类
 */
public abstract  class BaseAccessTokenHandler {
   private  static final Logger log= LoggerFactory.getLogger(BaseAccessTokenHandler.class);

   protected transient Oauth2Service oauth2Service= SpringBeanUtils.getBean(Oauth2Service.class);

   private ClientDetails clientDetails;

  protected  ClientDetails getclientDetails(){
      if(clientDetails==null){
         String clientid=getClientid();
         clientDetails=oauth2Service.loadClientDetails(clientid);
      }
      return clientDetails;
  }

    /**
     * 返回创建的token信息
     * @param token
     * @param jsonorquery
     * @return
     * @throws OAuthSystemException
     */
  protected OAuthResponse createAccessToken(AccessToken token, boolean jsonorquery) throws OAuthSystemException {
      final  ClientDetails clientDetails=getclientDetails();
       log.debug("create accesstoken");
       //redirecturi token expiresIn TokenType refreshToken
       final OAuthASResponse.OAuthTokenResponseBuilder builder= OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK)
                                                                        .location(clientDetails.getRedirectUri())
                                                                        .setAccessToken(token.getTokenvalue())
                                                                        .setTokenType(token.getTokenType());

       //refreshToken
       if(!(token.getRefreshToken() ==null)){
           RefreshToken refreshToken=token.getRefreshToken();
           builder.setRefreshToken(refreshToken.getRefreshvalue());
       }

       log.debug("存在ID Token...........{}",token.getIdMap().size());
       //idToken
      if(!token.getIdMap().isEmpty()){
            builder.setParam(AbstractOIDC.ID_TOKEN, String.valueOf(token.getIdMap().get(AbstractOIDC.ID_TOKEN)));
       }

       token.getIdMap().clear();

      return  jsonorquery ? builder.buildQueryMessage():builder.buildJSONMessage();
  }


    protected void invalidApprovalDeny(SoauthAuthRequest soauthAuthRequest,HttpServletResponse response) throws IOException, OAuthSystemException {

        final OAuthResponse oAuthResponse = OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND)
                .setError(OAuthError.CodeResponse.ACCESS_DENIED)
                .setErrorDescription("User denied access")
                .location(getclientDetails().getRedirectUri())
                .setState(soauthAuthRequest.getState())
                .buildQueryMessage();
        log.debug("'ACCESS_DENIED' response: {}", oAuthResponse);

      BaseSoauthResponse.SuccessResponse.oauthQueryResponse(response,oAuthResponse);

        //user logout when deny
        final Subject subject = SecurityUtils.getSubject();
        subject.logout();
        log.debug("After 'ACCESS_DENIED' call logout. user: {}", subject.getPrincipal());
    }

    /**
     *  token 失效时返回的信息
     * @param accessToken
     * @throws OAuthSystemException
     */
    protected void expiredTokennews(AccessToken accessToken, HttpServletResponse response) throws  OAuthSystemException{
        final ClientDetails clientDetails=getclientDetails();
        log.debug("AccessToken 过期{}"+accessToken.getId());

        final OAuthResponse oAuthResponse = OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND)
                .setError(OAuthError.ResourceResponse.EXPIRED_TOKEN)
                .setErrorDescription("access_token '" +accessToken.getId() + "' expired")
                .setErrorUri(clientDetails.getRedirectUri())
                .buildJSONMessage();

        BaseSoauthResponse.SuccessResponse.oauthjsonResponse(response,oAuthResponse,false);
    }

    /**
     *从AuthzRequest中获取客户端id
     * @return clientid
     */
   protected  abstract String getClientid();
}
