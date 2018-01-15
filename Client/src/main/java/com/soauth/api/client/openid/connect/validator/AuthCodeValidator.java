package com.soauth.api.client.openid.connect.validator;

import com.soauth.api.client.model.AuthenticationToken;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author zhoujie
 * @date 2017/12/12
 */
public class AuthCodeValidator  extends  AbstractValidator{

    private Logger log= LoggerFactory.getLogger(AuthCodeValidator.class);


     protected  HttpServletRequest request;
    public AuthCodeValidator(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        this.request=request;

    }

     public AuthenticationToken connectAuthToken(){

        if(!validateSelf()){
            return null;
        }

        return  loadAuthentToken(GrantType.AUTHORIZATION_CODE);
     }
    @Override
    protected boolean validateSelf() {
       validateState();

        return true;
    }


}
