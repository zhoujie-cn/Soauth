package com.soauth.server.oauth.message;

import com.soauth.core.vo.oauth2.AccessToken;
import com.soauth.core.vo.oauth2.ClientDetails;
import com.soauth.server.service.Oauth2Service;
import org.apache.commons.lang.StringUtils;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhoujie on 2017/10/8.
 *
 * 返回token信息类
 */
public abstract  class SoauthHandler {
   private  static final Logger log= LoggerFactory.getLogger(SoauthHandler.class);

   @Autowired()
   protected transient Oauth2Service oauth2Service;

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

       //redirecturi token expiresIn TokenType refreshToken
       final OAuthASResponse.OAuthTokenResponseBuilder builder= OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK)
                                                                        .location(clientDetails.redirectUri())
                                                                        .setAccessToken(token.tokenId())
                                                                        .setExpiresIn(String.valueOf(token.currentTokenExpiredSeconds()))
                                                                        .setTokenType(token.tokenType());
       String refreshToken=token.refreshToken();
       if(StringUtils.isNotEmpty(refreshToken)){
           builder.setRefreshToken(refreshToken);
       }

      return  jsonorquery ? builder.buildQueryMessage():builder.buildJSONMessage();
  }

   protected  abstract String getClientid();
}
