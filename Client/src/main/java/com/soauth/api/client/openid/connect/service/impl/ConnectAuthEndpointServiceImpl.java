package com.soauth.api.client.openid.connect.service.impl;
import com.soauth.api.client.model.ServerConfig;
import com.soauth.api.client.openid.connect.service.ConnectAuthEndpointService;
import com.soauth.api.client.openid.connect.service.ConnectServerConfigService;
import com.soauth.api.client.openid.connect.service.business.AuthReuqestUrlBuilder;
import com.soauth.core.vo.oauth2.AbstractOIDC;
import org.apache.oltu.oauth2.common.OAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Map;

/**
 *
 * @author zhoujie
 * @date 2017/11/29
 */
@Service
public class ConnectAuthEndpointServiceImpl implements ConnectAuthEndpointService {

    /**
     * ªÒ»°server≈‰÷√
     */
    @Autowired
    ConnectServerConfigService configService;
   private static  String enc="utf-8";

    @Override
    public String connectauthuri(HttpSession session, String scope,String clientsecret,  String clientid, String responseType, String redirectUri, Map<String,String> options){


        AuthReuqestUrlBuilder urlBuilder = new AuthReuqestUrlBuilder();

        return  urlBuilder.authbuilderUrl(getServerConfig(),scope,clientsecret,clientid,responseType,redirectUri,createNonce(session),createState(session),options);
    }

    @Override
    public ServerConfig getServerConfig(){
        return configService.loadServerConfig("http://localhost:8000/SoauthServer");
    }

    private String  createNonce(HttpSession session){
        String nonce=new BigInteger(50,new SecureRandom()).toString(18);
        ServletContext servletContext=session.getServletContext();
            servletContext.setAttribute(AbstractOIDC.OIDC_NONCE,nonce);
            return nonce;
    }

    private String  createState(HttpSession session){
        String state=new BigInteger(50,new SecureRandom()).toString(18);
        ServletContext servletContext=session.getServletContext();
        servletContext.setAttribute(OAuth.OAUTH_STATE,state);
        return state;
    }

}
