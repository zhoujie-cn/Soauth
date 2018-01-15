package com.soauth.api.client.openid.connect.service.impl;

import com.soauth.api.client.model.AuthenticationToken;
import com.soauth.api.client.openid.connect.service.AuthenticationTokenService;
import com.soauth.api.client.openid.connect.validator.AuthCodeValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author zhoujie
 * @date 2017/12/12
 */
@Service
public class AuthenticationTokenServiceImpl implements AuthenticationTokenService {

    private Logger log= LoggerFactory.getLogger(AuthenticationTokenServiceImpl.class);
    @Override
    public AuthenticationToken getCodeAuthentToken(HttpServletRequest request, HttpServletResponse response) {

        AuthCodeValidator validator = new AuthCodeValidator(request,response);

        validator.connectAuthToken();

        return null;
    }

    @Override
    public AuthenticationToken getImplicitToken(HttpServletRequest request, HttpServletResponse response) {
        parseToken(request);
        return null;
    }

    private AuthenticationToken parseToken(HttpServletRequest request){
        log.debug("parse Token the accessToken{}",request.getParameter("access_token"));
    Map hashMap=  request.getParameterMap();

        hashMap.forEach((k,v)->{
            log.debug("key{}...... value{}",k,v);
        });
        return  null;
    }


}
