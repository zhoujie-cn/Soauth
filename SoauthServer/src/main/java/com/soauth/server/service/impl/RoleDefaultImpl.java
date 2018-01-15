package com.soauth.server.service.impl;

import com.soauth.server.service.RoleDefault;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 *
 * @author zhoujie
 * @date 2017/11/1
 */

@Service
public class RoleDefaultImpl implements RoleDefault {

    @Override
    public Set<String> loadUserRole(String userid) {
        return null;
    }
}
