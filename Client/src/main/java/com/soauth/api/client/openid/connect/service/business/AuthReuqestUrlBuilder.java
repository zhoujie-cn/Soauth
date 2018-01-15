package com.soauth.api.client.openid.connect.service.business;
import com.soauth.api.client.model.ServerConfig;
import com.soauth.core.vo.oauth2.AbstractOIDC;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.oltu.oauth2.common.OAuth;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhoujie
 * @date 2017/11/28
 *
 */
public class AuthReuqestUrlBuilder {

      public String  authbuilderUrl(ServerConfig serverConfig,String scope,String clientsecret,String clientid,String responseType, String redirectUri, String nonce, String state, Map<String, String> options){

          List<NameValuePair> nameValuePairs= builderParams( options);
          try {
              return new URIBuilder(serverConfig.getAuthorizationEndpointUri()).addParameters(nameValuePairs).
                       addParameter(OAuth.OAUTH_RESPONSE_TYPE,responseType)
                      .addParameter(OAuth.OAUTH_CLIENT_ID,clientid)
                      .addParameter(OAuth.OAUTH_CLIENT_SECRET,clientsecret)
                      .addParameter(OAuth.OAUTH_SCOPE,scope)
                      .addParameter(OAuth.OAUTH_REDIRECT_URI,redirectUri)
                      .addParameter(AbstractOIDC.OIDC_NONCE,nonce)
                      .addParameter(OAuth.OAUTH_STATE,state)
                      .build().toString();

          } catch (URISyntaxException e) {
              e.printStackTrace();
          }
          return  null;
      }

    private List<NameValuePair> builderParams(  Map<String, String> options){
        List<NameValuePair> params = new ArrayList<>();
        if(!options.isEmpty()){
             options.forEach((k,v)->{
                 params.add(new BasicNameValuePair(k,v));
             });
        }
        return  params;
    }
}
