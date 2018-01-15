package com.soauth.server.web.authncontroller;

import com.soauth.core.utils.RsaKeyUtils;
import com.soauth.server.oauth.SoauthTokenRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwt.JwtClaims;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;
import java.security.interfaces.RSAPublicKey;

/**
 *
 * @author zhoujie
 * @date 2017/11/20
 *
 * 端点api
 * Client端获取公钥接口, SessionEndpoint接口
 */

@Controller
public class PublishingEndpoint {

    private static final String STATE = "state";

    private  boolean singleton=false;
    private static String path="keys/RSA2048public.key";

    @RequestMapping(value = "jwks", method = {RequestMethod.GET},produces = MediaType.APPLICATION_JSON_VALUE)
    public void publicJwks(HttpServletResponse response, HttpServletRequest request){

        response.setContentType("application/json; charset=utf-8");
        JsonWebKey rsa = null;
        try {
            RSAPublicKey jwk= (RSAPublicKey) RsaKeyUtils.getPublicKey(singleton,path);

             rsa= new RsaJsonWebKey(jwk);
            rsa.setAlgorithm("RSA");
            rsa.setKeyId("publibkey");
            Writer writer= response.getWriter();

            writer.write(rsa.toJson());

            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     *   返回给浏览器端 app端 登录的公钥
     * @return
     */
    @RequestMapping(value = "pubkey", method = {RequestMethod.GET},produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String publickeys(){
        RSAPublicKey jwk=null;
        try {
            jwk= (RSAPublicKey) RsaKeyUtils.getPublicKey(singleton,path);


        } catch (Exception e) {
            e.printStackTrace();
        }

        BASE64Encoder bse=new BASE64Encoder();

        String pkString=bse.encode(jwk.getEncoded());

        return pkString;
    }


    /**
     * doc: http://openid.net/specs/openid-connect-session-1_0.html
     *请求参数在文档第五节
     * @param idtokenHint
     * @param state
     * @param postlogoutRedirect
     * @return
     */
    @RequestMapping(value = "endSessionEndpoint",method = {RequestMethod.GET})
    public  String endSessionEndpoint(@RequestParam(value = "id_token_hint", required = false)String idtokenHint, @RequestParam(value =STATE, required = false)String state,
                                      @RequestParam(value = "post_logout_redirect_uri", required = false)String postlogoutRedirect, HttpServletRequest request, HttpServletResponse response){

        if(StringUtils.isEmpty(idtokenHint)){

            SoauthTokenRequest tokenRequest=null;
            JwtClaims idToken=null;
            try {
                tokenRequest= new  SoauthTokenRequest(request);
            } catch (OAuthSystemException e) {
                e.printStackTrace();
            } catch (OAuthProblemException e) {
                e.printStackTrace();
            }
            // 初始化验证器,执行验证         

        }

       return "index";
    }


}
