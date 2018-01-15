package com.soauth.core.shiro.utils;

import com.soauth.core.model.SigninToken;
import com.soauth.core.model.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhoujie
 * @date 2017/9/27
 *
 */
public class ShiroUtils {

    private static  final Logger log = LoggerFactory.getLogger(ShiroUtils.class);

    /**
     *  登录
     * @param entity
     * @param rememberMe
     * @return
     */
    public static UserInfo login(UserInfo  entity, Boolean rememberMe){
        SigninToken token = new SigninToken (entity.getUsername(), entity.getPassword());

        log.debug("token remember"+rememberMe);

        token.setRememberMe(rememberMe);
        SecurityUtils.getSubject().login(token);

        return currentUser();
    }

    /**
     *  获取当前用户
     * @return
     */
    public static UserInfo currentUser(){
        return (UserInfo) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 当前用户是否登录
     */
    public static boolean whetherLogin(){
        return  SecurityUtils.getSubject().getPrincipal()==null;
    }


    /**
     * 获取当前用户session
     * @return
     */
    public static Session getSession(){
        return SecurityUtils.getSubject().getSession();
    }


}
