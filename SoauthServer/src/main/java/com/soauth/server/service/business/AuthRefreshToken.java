package com.soauth.server.service.business;

import com.soauth.core.openid.connect.signature.DefaultSignatrueAndverifySignatrue;
import com.soauth.core.vo.oauth2.ClientDetails;
import com.soauth.core.vo.oauth2.RefreshToken;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;

/**
 *
 * @author zhoujie
 * @date 2017/12/5
 */
@Service
public class AuthRefreshToken {

    private static Logger logger = LoggerFactory.getLogger(AuthRefreshToken.class);

    public  JwtClaims createRefreshToken(String issuer, String username){

        JwtClaims claims = new JwtClaims();
          //调用refreshToken里面的
        claims.setExpirationTimeMinutesInTheFuture(RefreshToken.REFRESH_TOKEN_VALIDITY_SECONDS);
        claims.setGeneratedJwtId();
        claims.setIssuedAtToNow();
        claims.setIssuer(issuer);
        claims.setSubject(username);

       return   claims;
    }
}
