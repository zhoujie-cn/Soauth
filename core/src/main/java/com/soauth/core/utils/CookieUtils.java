package com.soauth.core.utils;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author zhoujie
 * @date 2017/10/30
 *
 * 封装系统的cookie业务
 */
public class CookieUtils {

    /**
     * 获取一个指定的Cookie值
     * @param request
     * @param key
     * @return
     */
    public static Cookie getCookie(HttpServletRequest request, String key){
        Cookie[] cookies = request.getCookies();

        if(null == cookies || cookies.length == 0) {
            return null;
        }

        for(int i=0; i<cookies.length; i++){
             Cookie cookie= cookies[i];

             if(cookie.getName().equals(key)){
                 return cookie;
             }
        }
        return null;
    }

    /**
     * 设置cookie
     *
     * @param response
     * @param name
     *            cookie名字
     * @param value
     *            cookie值
     * @param maxAge
     *            cookie生命周期 以秒为单位
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
            Cookie cookie = new Cookie(name, value);
            if (maxAge > 0) {
                cookie.setMaxAge(maxAge);
            }
            cookie.setPath("/");
            response.addCookie(cookie);
    }

}
