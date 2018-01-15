package com.soauth.core.model;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 *
 * @author zhoujie
 * @date 2017/9/25
 * 用户角色
 */
@Data
public class UURoles extends IdDomain {
    private static final long serialVersionUID = -8610039723429535699L;

    private Long id;
    /**角色名称*/
    private String name;
    /**角色类型*/
    private String type;
    /** 做 role --> permission 一对多处理*/
    private List<UPermission> permissions= Lists.newArrayList();

}
