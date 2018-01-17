package com.soauth.server.service;

import com.soauth.core.model.UserInfo;
import java.util.List;
import java.util.Map;
/**
 *
 * @author zhoujie
 * @date 2017/11/1
 *
 * 把用户 crud 操作全部放在此接口中
 */
public interface UserInfoService {

    /**
     * 获取用户信息
     * @param username
     * @return
     */
    UserInfo getUserInfo(String username);

    /**
     * 登录验证账号密码
     * @param username
     * @param password
     * @return
     */
    UserInfo login(String username,String password);


}
