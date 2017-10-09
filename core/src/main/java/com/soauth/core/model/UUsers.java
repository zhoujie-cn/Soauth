package com.soauth.core.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by zhoujie on 2017/9/25.
 * user对象实体
 */
@Data
public class UUsers extends  IdDomain{

    private static final long serialVersionUID = -8265820344361890793L;

    //0:禁止登录
    public static final Long _0 = new Long(0);
    //1:有效
    public static final Long _1 = new Long(1);
    private Long id;
    /**昵称*/
    private String username;
    /**头像标识*/
    private String uuid;
    /**密码*/
    private transient String password;
    /**创建时间*/
    private Date createTime;
    /**最后登录时间*/
    private Date lastLoginTime;
    /**1:有效，0:禁止登录*/
    private Long status;
    /**盐值*/
    private String salt;
    /** 部门*/
    private String department;


}
