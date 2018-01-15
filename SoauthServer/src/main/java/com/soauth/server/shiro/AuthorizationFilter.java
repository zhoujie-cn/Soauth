package com.soauth.server.shiro;

import com.soauth.core.shiro.utils.ShiroUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 * @author zhoujie
 * @date 2017/11/12
 *
 * 拦截authorize
 */
public class AuthorizationFilter extends AccessControlFilter{

    private static final Logger logger = LoggerFactory.getLogger(AccessControlFilter.class);

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        if(!ShiroUtils.whetherLogin()|| !isLoginRequest(request,response)){
             logger.debug("用户未登录,转向登录方法...");
             return true;
        }

        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        saveRequestAndRedirectToLogin(request,response);
        return false;
    }
}
