package com.soauth.core.vo.oauth2;

import com.soauth.core.vo.AbstarctVo;

/**
 * Created by zhoujie on 2017/9/25.
 */
public class Oauth2Code extends AbstarctVo{

    private static final long serialVersionUID = -2567465938303275072L;
    private String code;

    private String username;

    private String clientId;

    public Oauth2Code() {
    }


    public String code() {
        return code;
    }

    public Oauth2Code code(String code) {
        this.code = code;
        return this;
    }

    public String username() {
        return username;
    }

    public Oauth2Code username(String username) {
        this.username = username;
        return this;
    }

    public String clientId() {
        return clientId;
    }

    public Oauth2Code clientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

}
