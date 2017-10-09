package com.soauth.server.oauth.validator.authzGrant;

import com.soauth.server.oauth.SoauthAuthRequest;
import com.soauth.server.oauth.message.SoauthResponse;
import com.soauth.server.oauth.validator.client.ClientValidator;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhoujie on 2017/9/22.
 * 适配并且验证 authorize类型 code token等
 */
public  abstract class AuthzHandlerValidator {
     private static final Logger log = LoggerFactory.getLogger(AuthzHandlerValidator.class);

     protected SoauthAuthRequest soauthAuthRequest;
     protected HttpServletResponse response;

     public AuthzHandlerValidator(SoauthAuthRequest soauthAuthRequest, HttpServletResponse response){
          this.response=response;
          this.soauthAuthRequest=soauthAuthRequest;
     }


    protected  abstract ClientValidator  getValidator();

      public void  verify(){

      }

    /**
     *  检查客户端参数
     * @return
     */
    protected boolean checkClientValidator() throws OAuthSystemException {
          ClientValidator validator= getValidator();
          log.debug("checkClientValidator{} client id{}",validator,soauthAuthRequest.getClientId());
          final OAuthResponse response=validator.validate();

          if(response ==null){
              return  false;
          }
          else{
              SoauthResponse.SuccessResponse.oauthjsonResponse(this.response,response);
              return  true;
          }

      }
}
