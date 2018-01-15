package com.soauth.server.dao;
import com.soauth.core.model.UserInfo;

/**
 *
 * @author zhoujie
 * @date 2017/11/1
 */
public interface UserDefaultdao {

    /**
     * 从数据库中获取用户信息
     * @param username
     * @return
     */
   UserInfo getUserInfo(String username);

   UserInfo verifyUser(String username, String password);
}
