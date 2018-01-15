package com.soauth.api.client.openid.connect.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soauth.api.client.model.AuthenticationToken;
import com.soauth.api.client.model.ServerConfig;
import com.soauth.api.client.openid.connect.service.ClientConfigService;
import com.soauth.api.client.openid.connect.service.ConnectServerConfigService;
import com.soauth.core.openid.connect.JwksCacheService;
import com.soauth.core.openid.connect.encryption.DefaultJwtencryptAnddecryption;
import com.soauth.core.openid.connect.signature.DefaultSignatrueAndverifySignatrue;
import com.soauth.core.utils.SpringBeanUtils;
import com.soauth.core.vo.oauth2.AbstractOIDC;
import com.soauth.core.vo.oauth2.ClientDetails;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.jose4j.base64url.Base64;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.PublicJsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;

/**
 *
 * @author zhoujie
 * @date 2017/11/29
 */
public abstract  class AbstractValidator {

    private Logger log= LoggerFactory.getLogger(this.getClass());

    protected  static  int  Http_SOCKET_TIMEOUT=30000;

    protected DefaultSignatrueAndverifySignatrue signatrue= SpringBeanUtils.getBean("defualtSignatrueAndverifySignatrue");

    protected DefaultJwtencryptAnddecryption jwtencryptAnddecryption=SpringBeanUtils.getBean("defaultJwtencryptAnddecryption");

    protected JwksCacheService jwksCacheService=SpringBeanUtils.getBean("jwksCacheService");

    protected   static String  SESSION_ISSUER="http://localhost:8000/SoauthServer";

    protected static String CHARSET_NAME="UTF-8";


    ConnectServerConfigService configService= SpringBeanUtils.getBean(ConnectServerConfigService.class);


    ClientConfigService clientConfigService = SpringBeanUtils.getBean(ClientConfigService.class);

    HttpServletResponse response;
    HttpServletRequest request;

   public  AbstractValidator(HttpServletRequest request, HttpServletResponse response){
       this.response=response;
       this.request=request;
   }


     protected  ServerConfig getServerConfig(){
       return  configService.loadServerConfig(SESSION_ISSUER);
     }
    /**
     * 像OP请求签名的json字符串, 验证签名加密后返回
     * @param grantType
     * @return
     */
    protected   AuthenticationToken loadAuthentToken(GrantType grantType){

        MultiValueMap<String, String> submitform = new LinkedMultiValueMap<>();

        String authorizationCode=  request.getParameter(OAuth.OAUTH_CODE);


        //获取返回的服务器配置
        ServerConfig serverConfig=getServerConfig();

        //获取默认的客户端信息
        ClientDetails clientDetails=clientConfigService.getClientConfig(serverConfig.getIssuer());

        String clientid= clientDetails.getClientId();
        String clientSecret=clientDetails.getClientSecret();

        submitform.add(OAuth.OAUTH_GRANT_TYPE,grantType.toString());
        submitform.add(OAuth.OAUTH_CODE,authorizationCode);
        submitform.add(OAuth.OAUTH_REDIRECT_URI,clientDetails.getRedirectUri());

        submitform.add(AbstractOIDC.OIDC_DISPLAY,request.getParameter(AbstractOIDC.OIDC_DISPLAY));

        submitform.add(OAuth.OAUTH_SCOPE, StringUtils.join(clientDetails.getScope()," "));

        HttpClient httpClient   = HttpClientBuilder.create()
                    .useSystemProperties()
                    .setDefaultRequestConfig(RequestConfig.custom()
                            .setSocketTimeout(Http_SOCKET_TIMEOUT)
                            .build())
                    .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);


        RestTemplate restTemplate;

        /**
         * document: http://openid.net/specs/openid-connect-core-1_0.html#ClientAuthentication
         */
        if(clientDetails.getTokenEndpointmethod().equals(ClientDetails.Authentication.SECRET_BASIC)){
            //client_secret_basic
            restTemplate = new RestTemplate(factory) {

                @Override
                protected ClientHttpRequest createRequest(URI url, HttpMethod method) throws IOException {
                    ClientHttpRequest httpRequest = super.createRequest(url, method);
                    httpRequest.getHeaders().add("Authorization",
                            String.format("Basic %s", Base64.encode(String.format("%s:%s",
                                    UriUtils.encodePathSegment(clientid, CHARSET_NAME),
                                    UriUtils.encodePathSegment(clientSecret, CHARSET_NAME)).getBytes(CHARSET_NAME))));

                    return httpRequest;
                }
            };


        }else {
            //client_secret_jwt
            restTemplate = new RestTemplate(factory);
            JwtClaims claims = new JwtClaims();

        }
       log.debug("get TokenEndpointUri {}",serverConfig.getAuthorizationEndpointUri());

       String token=  restTemplate.postForObject(serverConfig.getTokenEndpointUri(),submitform,String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        AuthenticationToken  authenticationToken=null;
        try {
          authenticationToken= objectMapper.readValue(token,AuthenticationToken.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

       return  validateJwtSignatrue(authenticationToken,serverConfig.getJwksUri());
    }


    protected static  String getParamSession(HttpSession session, String key) {
        ServletContext servletContext=session.getServletContext();
        String  obj =servletContext.getAttribute(key).toString();

        if (obj!= null) {
            return obj;
        } else {
            return null;
        }
    }

    protected  boolean validateState(){
        final String state= request.getParameter(OAuth.OAUTH_STATE);

        final String storedState =getParamSession(request.getSession(), OAuth.OAUTH_STATE);

        //validate state
        if(storedState == null || !storedState.equals(state)){
            log.debug("state和stroredState不一致, state为{}"+state+ "   stroredstate为{}"+storedState);
             invalidState(state);
             return  false;
        }
        return  true;
    }

     protected  void  invalidState(String state){

         try {
             final OAuthResponse oAuthResponse = OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND)
                     .setError(OAuthError.CodeResponse.ACCESS_DENIED)
                     .setErrorDescription("state 不一致")
                     .setState(state)
                     .buildQueryMessage();
         } catch (OAuthSystemException e) {
             e.printStackTrace();
         }
     }


    /**
     * 从OP加载公钥并验证OP传输的jws token是否为OP发放. 即验签
     *
     * @param jwsvalue
     * @param jwkurl
     * @return  验签完后的Token
     */
     protected  AuthenticationToken  validateJwtSignatrue(AuthenticationToken jwsvalue, String jwkurl){
        log.debug("从op 加载jwks");
        String key= (String) jwksCacheService.getPublicKey(jwkurl);

         String accessToken;
         String refreshToken;
         String idToken;
         PublicKey  pk=null;
         try {

             PublicJsonWebKey jsonWebKey= PublicJsonWebKey.Factory.newPublicJwk(key);

             pk=jsonWebKey.getPublicKey();

             //验证签名
             if(StringUtils.isNotEmpty(jwsvalue.getAccessToken())) {
                 accessToken = signatrue.verifyJwtSignature(pk, jwsvalue.getAccessToken());
                 log.debug("签名验证成功(accessToken ) ");
                 jwsvalue.setAccessToken(accessToken);
             }

             if(StringUtils.isNotEmpty(jwsvalue.getRefreshToken())) {
                 refreshToken = signatrue.verifyJwtSignature(pk, jwsvalue.getRefreshToken());
                 log.debug("签名验证成功(refreshToken ) ");
                 jwsvalue.setRefreshToken(refreshToken);
             }
             if(StringUtils.isNotEmpty(jwsvalue.getIdToken())) {
                 idToken = signatrue.verifyJwtSignature(pk, jwsvalue.getIdToken());
                 log.debug("签名验证成功(idToken ) ");
               jwsvalue.setIdToken(idToken);
             }

         } catch (JoseException e) {

             e.printStackTrace();
         }

         return encryptJwt(pk,jwsvalue);
     }

    /**
     *  对Token进行加密
     * @param key
     * @param authenticationToken
     * @return
     */
     protected  AuthenticationToken encryptJwt(PublicKey key, AuthenticationToken authenticationToken) {
         RSAPublicKey jwk = (RSAPublicKey) key;
         JsonWebKey rsaKey = new RsaJsonWebKey(jwk);

         String accessToken;
         String refreshToken;
         String idToken;

         try {
             if (StringUtils.isNotEmpty(authenticationToken.getAccessToken())) {
                 accessToken = jwtencryptAnddecryption.encryptJwtAsrsa256(rsaKey, authenticationToken.getAccessToken());
                 log.debug("加密完成(accessToken)");
                 authenticationToken.setAccessToken(accessToken);
             }

             if (StringUtils.isNotEmpty(authenticationToken.getRefreshToken())) {
                 refreshToken = jwtencryptAnddecryption.encryptJwtAsrsa256(rsaKey, authenticationToken.getRefreshToken());
                 log.debug("加密完成(refreshToken)");
                 authenticationToken.setRefreshToken(refreshToken);
             }

             if (StringUtils.isNotEmpty(authenticationToken.getIdToken())) {
                 idToken = jwtencryptAnddecryption.encryptJwtAsrsa256(rsaKey, authenticationToken.getIdToken());
                 log.debug("加密完成(idToken)");
                 authenticationToken.setIdToken(idToken);
             }

         } catch (JoseException e){
             e.printStackTrace();
         }
         return  authenticationToken;
     }

    protected abstract boolean  validateSelf();
}
