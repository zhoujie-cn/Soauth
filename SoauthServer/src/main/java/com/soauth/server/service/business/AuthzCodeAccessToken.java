package com.soauth.server.service.business;

import com.soauth.core.utils.SpringBeanUtils;
import com.soauth.core.vo.oauth2.AccessToken;
import com.soauth.core.vo.oauth2.ClientDetails;
import com.soauth.core.vo.oauth2.Oauth2Code;
import com.soauth.server.oauth.SoauthTokenRequest;
import com.soauth.server.service.TokenService;
import org.apache.oltu.oauth2.common.OAuth;
import org.jose4j.jwt.JwtClaims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


/**
 *
 * @author zhoujie
 * @date 2017/11/9
 */



public class AuthzCodeAccessToken extends  AbstractOIDCHolder {

    private  static Logger log = LoggerFactory.getLogger(AuthzCodeAccessToken.class);


    private String issuer;


    public AccessToken createNewAccessToken(ClientDetails clientDetails, String ucode, SoauthTokenRequest request){

            final Oauth2Code code= getCode(clientDetails.getClientId(),ucode);
            final String username=code.username();

            AccessToken accessToken;
            JwtClaims claims= new JwtClaims();

            // value÷µΩ‚ Õ:http://openid.net/specs/openid-connect-core-1_0.html#IDToken
           claims.setIssuer(issuer);
           claims.setAudience(clientDetails.getClientId());
           claims.setSubject(username);
           claims.setIssuedAtToNow();
           claims.setGeneratedJwtId();

      accessToken = tokenBuilder.enhance(clientDetails,claims, request,clientDetails.getClientId());

           return  accessToken;
    }




    protected Oauth2Code getCode(String clientid, String code ){
        log.debug("token Code params clientid , code={}{}",clientid,code);
        return tokenService.findOauth2Code(clientid,code);

    }


}
