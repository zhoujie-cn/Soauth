package com.soauth.server.service;

import java.util.Set;

/**
 *
 * @author zhoujie
 * @date 2017/11/1
 *
 * 用户权限相关的crud方法放在此接口
 */
public interface RoleDefault {
    /**
     * 加载用户角色
     * @param userid
     * @return
     */
    Set<String> loadUserRole(String userid);
}
