package com.soauth.core.vo.oauth2;

import com.soauth.core.utils.DateUtils;
import com.soauth.core.vo.AbstarctVo;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 *
 * @author zhoujie
 * @date 2017/9/25
 */
public class Oauth2Code {

    @Getter
    @Setter
    private Long id;

    private static final long serialVersionUID = -2567465938303275072L;

    /**
     * Code 创建时间
     */
    private String createTime;
    /**
     * code 过期时间, 单位ms
     */
    public static  long  DEFAULT_CODE_EXPIRATION= 1800000;

    /**
     * Code 过期时间
     */
    private String explration;

    @Getter
    private  String codeChallenge;

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

    public Oauth2Code createtime(String date){
       createTime= date;
       return this;
    }

    public String createtime(){
        return  createTime;
    }

    public String explration(){

        return  explration;
    }

    public  Oauth2Code  explration( String date){
        explration=date;
        return this;
    }



}
