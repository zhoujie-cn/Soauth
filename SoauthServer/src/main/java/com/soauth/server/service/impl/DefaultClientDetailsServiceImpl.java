package com.soauth.server.service.impl;

import com.soauth.core.vo.oauth2.AbstractOIDC;
import com.soauth.core.vo.oauth2.ClientDetails;
import com.soauth.server.dao.ClientDetailsdao;
import com.soauth.server.model.ClientInfoPage;
import com.soauth.server.oauth.authorize.ServerConfig;
import com.soauth.server.service.ClientDetailsService;
import org.apache.commons.lang.StringUtils;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author zhoujie
 * @date 2017/12/15
 */
@Service
public class DefaultClientDetailsServiceImpl implements ClientDetailsService {

    @Autowired
    ClientDetailsdao clientDetailsdao;

    @Autowired
    ServerConfig serverConfig;

    /**
     *  添加client, 并为每个client添加基本api请求(请求登录, 请求授权, 请求刷新)
     * @param clientDetails
     * @return
     */
    @Override
    public  boolean saveClientDetails(ClientDetails clientDetails) {

        addToken(clientDetails);

        checkandAddJwk(clientDetails);

        checkGrantTypes(clientDetails);

        clientDetails.setGrantType(Arrays.toString(new Set[]{clientDetails.getGrantTypes()}));
        clientDetails.setScopeString(Arrays.toString(new Set[]{clientDetails.getScope()}));

       int rows= clientDetailsdao.saveClientDetails(clientDetails);
        return rows>0;
    }

    @Override
    public List<ClientInfoPage> getAllClientDetails(ClientInfoPage clientInfoPage) {

        return clientDetailsdao.getAllClientDetails(clientInfoPage);
    }

    /**
     *  注册的客户端不能同时拥有jwkurl 和 jwkValue
     * @param clientDetails
     */
    private  void checkandAddJwk(ClientDetails clientDetails){

        if(clientDetails.getJwk()!=null && !StringUtils.isNotEmpty(clientDetails.getJwksUri().trim())){
            throw  new IllegalArgumentException("注册的客户端不能同时拥有jwkurl 和 jwkValue");
        }

        //如果管理员没有指定client jwkurl则为其添加默认的jwk获取地址
        if(!StringUtils.isNotEmpty(clientDetails.getJwksUri())){
            String jwkUri=serverConfig.getJwkUri();
            clientDetails.setJwksUri(jwkUri);
        }
    }

    private void addToken(ClientDetails clientDetails){
        Set<String>  set = new LinkedHashSet<>();


        if(!clientDetails.getGrantTypes().contains(OAuth.OAUTH_REFRESH_TOKEN) && !clientDetails.getGrantTypes().contains(GrantType.CLIENT_CREDENTIALS) &&!clientDetails.getGrantTypes().contains(AbstractOIDC.OAUTH_Implicit))
        {
            set.add(OAuth.OAUTH_REFRESH_TOKEN);
        clientDetails.setGrantTypes(set);
            clientDetails.setScope(set);
        }

        //允许授权
        if(!clientDetails.getGrantTypes().contains(OAuth.OAUTH_ACCESS_TOKEN)){
            set.add(OAuth.OAUTH_ACCESS_TOKEN);
        }

        //允许登录
        if(!clientDetails.getGrantTypes().contains(AbstractOIDC.ID_TOKEN)){
            set.add(AbstractOIDC.ID_TOKEN);
        }
    }

    /**
     * 禁止使用password模式, 检查redirectUri, 检查客户端兼容性, 检查并添加ClientAuthentication
     * @param clientDetails
     */
    private  void  checkGrantTypes(ClientDetails clientDetails){


        if(!StringUtils.isNotEmpty(clientDetails.getRedirectUri())){
            throw  new IllegalArgumentException("客户端没有指定回调用地址");
        }

        if(clientDetails.getTokenEndpointmethod()==null){

            clientDetails.setTokenEndpointmethod(ClientDetails.Authentication.SECRET_BASIC);
        }

        if(clientDetails.getGrantTypes().contains(GrantType.AUTHORIZATION_CODE)){

            if(clientDetails.getGrantTypes().contains(GrantType.CLIENT_CREDENTIALS) ||clientDetails.getGrantTypes().contains(AbstractOIDC.OAUTH_Implicit)){
                throw  new IllegalArgumentException("不允许在注册authorization_code模式的同时在注册client_credentials模式 和 implicit 模式");
            }
        }

        if(clientDetails.getGrantTypes().contains(GrantType.CLIENT_CREDENTIALS)){

            if(clientDetails.getGrantTypes().contains(GrantType.AUTHORIZATION_CODE) ||clientDetails.getGrantTypes().contains(AbstractOIDC.OAUTH_Implicit)){
                throw  new IllegalArgumentException("不允许在注册client_credentials模式的同时在注册authorization_code模式 和 implicit 模式");
            }
        }

        if(clientDetails.getGrantTypes().contains(AbstractOIDC.OAUTH_Implicit)){

            if(clientDetails.getGrantTypes().contains(GrantType.AUTHORIZATION_CODE) ||clientDetails.getGrantTypes().contains(GrantType.CLIENT_CREDENTIALS)){
                throw  new IllegalArgumentException("不允许在注册implicit模式的同时在注册authorization_code模式 和client_credentials模式");
            }
        }


        if(clientDetails.getGrantTypes().contains(GrantType.PASSWORD)){
            throw  new IllegalArgumentException("授权服务器不支持OAuth2.0的password授权模式");
        }

    }

}
