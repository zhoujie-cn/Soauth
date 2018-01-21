package com.soauth.server.service.business;



import com.soauth.core.vo.oauth2.AbstractOIDC;
import com.soauth.core.vo.oauth2.AccessToken;
import com.soauth.core.vo.oauth2.ClientDetails;
import com.soauth.server.oauth.SoauthTokenRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *
 * @author zhoujie
 * @date 2017/11/2
 *
 * 创建idtoken
 */
@Service()
public class AuthorizationIdToken {

    @Value(value ="issuer")
    private  String issuer;


 public JwtClaims createIdToken(String clientid, OAuthRequest request, String subject, NumericDate issTime, JwtClaims claims){

     //如果是app端,则不要设置过期时间
     if(request.getParam(AbstractOIDC.OIDC_DISPLAY).equals(AbstractOIDC.Display.WAP.getDisplay())){
         claims.setExpirationTime(null);
     }else{
         claims.setExpirationTimeMinutesInTheFuture(AccessToken.ACCESS_TOKEN_VALIDITY_SECONDS);
     }

     claims.setIssuedAt(issTime);
     claims.setIssuer(issuer);
     claims.setAudience(clientid);
     claims.setSubject(subject);

     String nonce=request.getParam(AbstractOIDC.OIDC_NONCE);
     // nonce 原值返回用于防止重放攻击
     if(!StringUtils.isEmpty(nonce)){
         claims.setClaim(AbstractOIDC.OIDC_NONCE,nonce);
     }

     return  claims;
 }


}
