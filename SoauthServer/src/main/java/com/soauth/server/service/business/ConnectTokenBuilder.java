package com.soauth.server.service.business;

import com.soauth.core.openid.connect.signature.DefaultSignatrueAndverifySignatrue;
import com.soauth.core.utils.RsaKeyUtils;
import com.soauth.core.vo.oauth2.AbstractOIDC;
import com.soauth.core.vo.oauth2.AccessToken;
import com.soauth.core.vo.oauth2.ClientDetails;
import com.soauth.core.vo.oauth2.RefreshToken;
import com.soauth.server.oauth.SoauthTokenRequest;
import com.soauth.server.service.UserInfoService;
import com.soauth.server.service.TokenService;
import org.apache.oltu.oauth2.as.request.OAuthRequest;
import org.apache.oltu.oauth2.common.OAuth;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;

/**
 *
 * @author  zhoujie
 * @date 2017/12/4
 */

@Service()
public class ConnectTokenBuilder implements  TokenBuilder {

    private static  Logger logger = LoggerFactory.getLogger(ConnectTokenBuilder.class);

    @Autowired
    AuthorizationIdToken idToken;

    @Autowired
    TokenService tokenService;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    DefaultSignatrueAndverifySignatrue signatrue;

    @Autowired
    AuthRefreshToken authRefreshToken;

    private  static   PrivateKey pk;
    static{
         String path="keys/RSA2048private.key";
        try {
           pk= RsaKeyUtils.getPrivateKey(false,path);
        } catch (Exception e) {

            logger.error("无法获取到private key, 请检查配置 错误信息{}");
        }
    }


    /**
     *  创建idToken, 或者refreshToken, 或者accessToken.
     * @param claims
     * @param request
     * @param clientDetails
     * @param issuer
     * @return
     */
    @Override
    public AccessToken enhance(ClientDetails clientDetails, JwtClaims claims, OAuthRequest request, String issuer) {

        AccessToken accessToken= new AccessToken();
        claims.setExpirationTimeMinutesInTheFuture(AccessToken.ACCESS_TOKEN_VALIDITY_SECONDS);
        claims.setIssuedAt(NumericDate.now());
        String signtoken=null;
        String username=null;
        NumericDate now=null;

        try {
            username=claims.getSubject();
            now=claims.getIssuedAt();

           signtoken=signatrue.signatrueJwt(pk,claims.toString());
        } catch (Exception e) {

            if(e instanceof JoseException){
                logger.error("无法对accessToken进行签名 ");
                e.printStackTrace();
            }else {
                e.printStackTrace();
            }
        }

        //set client
        accessToken.setClientDetails(clientDetails);
        //set Scope
        accessToken.setScope(request.getScopes());
        // set accessToken
        accessToken.setTokenvalue(signtoken);

         logger.debug("scope..{}",accessToken.getScope().toString());
        // create RefershToken
        if(accessToken.getScope().contains(OAuth.OAUTH_REFRESH_TOKEN)) {

            RefreshToken refreshToken= enhanceRefreshToken(clientDetails, issuer, username);
            accessToken.setRefreshToken(refreshToken);
        }else{

            accessToken.setRefreshToken(null);
        }


        String compared="openid";
        //create idToken
        if(request.getScopes().contains(compared)){
           String idToken=enhanceIdToken(request,accessToken,username,clientDetails.getClientId(),now);
           accessToken.setIdToken(idToken);
        }

        return accessToken;

    }

    private  String enhanceIdToken( OAuthRequest request, AccessToken token, String username, String clientid, NumericDate issTime){
          JwtClaims claims = new JwtClaims();
          claims = idToken.createIdToken(clientid,request,username,issTime,claims);
        String signIdtoken=null;
        try {
         signIdtoken=signatrue.signatrueJwt(pk,claims.toString());
        } catch (JoseException e) {
            logger.error("无法对idToken进行签名");
            e.printStackTrace();
        }

        return signIdtoken;
    }


    private RefreshToken enhanceRefreshToken(ClientDetails clientDetails, String issuer, String username){

        String   refreshvalue=null;
        JwtClaims claims=authRefreshToken.createRefreshToken(issuer,username);
        RefreshToken refreshToken= new RefreshToken();
        try {

            refreshvalue=signatrue.signatrueJwt(pk,claims.toString());
        } catch (JoseException e) {
            logger.error("refreshtoken 签名错误 ");
            e.printStackTrace();
        }
        refreshToken.setRefreshvalue(refreshvalue);

        refreshToken.setClientDetails(clientDetails);

       return refreshToken;
    }



}
