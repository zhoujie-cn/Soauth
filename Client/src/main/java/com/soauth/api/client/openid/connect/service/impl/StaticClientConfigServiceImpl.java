package com.soauth.api.client.openid.connect.service.impl;

import com.soauth.api.client.openid.connect.service.ClientConfigService;
import com.soauth.core.vo.oauth2.ClientDetails;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 *
 * @author zhoujie
 * @date 2017/12/5
 *
 *øÕªß∂Àµƒæ≤Ã¨≈‰÷√
 */
public class StaticClientConfigServiceImpl implements ClientConfigService {

    @Getter
    @Setter
    private Map<String,ClientDetails> clientDetailsMap;


    @Override
    public ClientDetails getClientConfig(String issuer) {
        return clientDetailsMap.get(issuer);
    }

    @PostConstruct
    public  void  verifyEmptyornull(){

        if(clientDetailsMap.isEmpty() || clientDetailsMap==null){
            throw  new IllegalArgumentException("æ≤Ã¨øÕªß∂À≈‰÷√Œ™ø’. ");
        }
    }
}
