package com.soauth.core.model;

import lombok.Data;

/**
 * Created by zhoujie on 2017/9/25.
 *
 * 用户角色关联
 */
@Data
public class UUserRoles extends  IdDomain{
    private static final long serialVersionUID = 8447627981074916584L;

    private Long uid;

    private Long rid;

}
