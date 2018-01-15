package com.soauth.server.service.impl;

import com.soauth.core.vo.oauth2.AccessToken;
import com.soauth.core.vo.oauth2.Oauth2Code;
import com.soauth.core.vo.oauth2.RefreshToken;
import com.soauth.server.dao.Tokendao;
import com.soauth.server.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author zhoujie
 * @date 2017/12/5
 */

@Service()
public class TokenServiceImpl implements TokenService{

    private static Logger logger= LoggerFactory.getLogger(TokenServiceImpl.class);

    @Autowired
    Tokendao tokendao;

    @Override
    public AccessToken saveAccessToken(AccessToken accessToken) {
        return null;
    }

    @Override
    public RefreshToken saveRefreshToken(RefreshToken refreshToken) {
        return null;
    }

    @Override
    public Oauth2Code findOauth2Code(String clientid, String code) {
          logger.debug("search Code params clientid , code={}{}",clientid,code);
        Oauth2Code oauth2Code = tokendao.findOauth2Code(clientid,code);
        return oauth2Code;
    }

    @Override
    public Oauth2Code findOauth2CodeUserAndClientId(String username, String clientid) {
        logger.debug("search Code params username, clientid={}{}",username,clientid);
        return tokendao.findOauth2CodeUserAndClientId(username,clientid);
    }

    @Override
    public int deleteOauth2Code(Oauth2Code code) {
        return tokendao.deleteOauth2Code(code);
    }

    @Override
    public int saveOauth2Code(Oauth2Code code) {
        logger.info(" Save Code");
       return tokendao.saveOauth2Code(code);
    }


}
