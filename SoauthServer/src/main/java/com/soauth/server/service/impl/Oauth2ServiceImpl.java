package com.soauth.server.service.impl;

import com.soauth.core.vo.oauth2.ClientDetails;
import com.soauth.core.vo.oauth2.Oauth2Code;
import com.soauth.server.service.Oauth2Service;
import org.springframework.stereotype.Service;

/**
 * Created by zhoujie on 2017/9/26.
 */

@Service("authservice")
public class Oauth2ServiceImpl implements Oauth2Service {


    public ClientDetails loadClientDetails(String clientId) {
        return null;
    }


    public Oauth2Code loadOauthCode(String code, ClientDetails clientDetails) {
        return null;
    }

    @Override
    public int saveOauthcode(Oauth2Code code) {
        return 0;
    }
}
