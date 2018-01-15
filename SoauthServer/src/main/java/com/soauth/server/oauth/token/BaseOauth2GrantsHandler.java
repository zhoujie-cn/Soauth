package com.soauth.server.oauth.token;

import com.soauth.server.oauth.SoauthTokenRequest;
import com.soauth.server.oauth.message.BaseAccessTokenHandler;
import com.soauth.server.oauth.message.BaseSoauthResponse;
import com.soauth.server.oauth.validator.client.BaseClientValidator;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author zhoujie
 * @date 2017/10/10
 *
 * authorization , Client Credentials , Password Credentials,  Refreshing Token 的公有类
 */
public abstract  class BaseOauth2GrantsHandler extends BaseAccessTokenHandler implements SoauthHandler {
    private  final  static Logger log= LoggerFactory.getLogger(BaseOauth2GrantsHandler.class);

    protected SoauthTokenRequest tokenRequest;
    protected  HttpServletResponse response;



    @Override
    public  void handle(SoauthTokenRequest tokenRequest, HttpServletResponse response) throws OAuthProblemException, OAuthSystemException {
         this.tokenRequest=tokenRequest;
         this.response=response;
         // validate
         if(verify()){
           return ;
         }
         afterValidate();
    }

    protected boolean verify() throws OAuthSystemException{
      BaseClientValidator validator=getValidator();
        OAuthResponse response=validator.validate();

        if(response ==null){
            return  false;
        }
        else{
            BaseSoauthResponse.SuccessResponse.oauthjsonResponse(this.response,response,false);
            return  true;
        }

    }

    /**
     *  子类返回具体的验证类逻辑.
     *  通常位于validator包中
     * @return
     */
    protected abstract BaseClientValidator getValidator();

   @Override
   protected  String getClientid(){ return  tokenRequest.getClientId();}

    /**
     * 实现类实现验证完成后的操作步骤
     * @throws OAuthProblemException
     * @throws OAuthSystemException
     */
   protected abstract void afterValidate()throws OAuthProblemException, OAuthSystemException;
}
