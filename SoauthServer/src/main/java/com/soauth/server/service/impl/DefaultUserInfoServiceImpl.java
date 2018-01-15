package com.soauth.server.service.impl;

import com.soauth.core.model.UserInfo;
import com.soauth.core.utils.RsaKeyUtils;
import com.soauth.server.dao.UserDefaultdao;
import com.soauth.server.service.UserInfoService;
import org.jose4j.keys.RsaKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;

/**
 *
 * @author zhoujie
 * @date 2017/11/1
 */

@Service
public class DefaultUserInfoServiceImpl implements UserInfoService {

    @Autowired
    UserDefaultdao userDefault;

    @Override
    public UserInfo getUserInfo(String username) {

        return userDefault.getUserInfo(username);
    }

    @Override
    public UserInfo login(String username, String password) {

        String keypath="keys/RSA2048private.key";

        try {
            PrivateKey  pk= RsaKeyUtils.getPrivateKey(false,keypath);
            password = RsaKeyUtils.decryption(password,pk, RsaKeyUtil.RSA);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userDefault.verifyUser(username,password);
    }


}
