package com.soauth.core.model;

import lombok.Data;

/**
 * Created by zhoujie on 2017/9/25.
 */
@Data
public class UPermission {

    private Long id;
    /** 操作的url */
    private String url;
    /** 操作的名称 */
    private String name;
}
