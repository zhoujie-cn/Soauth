package com.soauth.api.client.openid.connect.service;

import com.soauth.api.client.model.AuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author zhoujie
 * @date 2017/12/12
 */
public interface AuthenticationTokenService {

    /**
     * 验证服务器端请求, 并像服务器端请求Token
     * @return
     */
    AuthenticationToken getCodeAuthentToken(HttpServletRequest request, HttpServletResponse response);

    /**
     * 简化模式下获取Token
     */
    AuthenticationToken getImplicitToken(HttpServletRequest request, HttpServletResponse response);
}
