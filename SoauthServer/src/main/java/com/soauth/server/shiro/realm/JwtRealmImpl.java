package com.soauth.server.shiro.realm;

import com.soauth.server.service.UserInfoService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author zhoujie
 * @date 2017/9/28
 * 验证用户username/password 查询role
 */
public class JwtRealmImpl extends AuthorizingRealm {

    private static final Logger log = LoggerFactory.getLogger(JwtRealmImpl.class);

    @Autowired
    UserInfoService userDefault;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
/*
        UserToken userToken=(UserToken)token;
        String username=String.valueOf(userToken.getUsername());
        UUsers users=userDefault.login(userToken.getUsername());
        log.debug(users.getUsername());
        log.debug(users.getSalt());
         String salt=users.getUsername()+users.getSalt();

       SimpleAccount simpleAccount = new SimpleAccount(users,users.getPassword(), ByteSource.Util.bytes(salt),getName());
*/
        return null;
    }
}
