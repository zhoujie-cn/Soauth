package com.soauth.core.model;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author zhoujie
 * @date 2017/9/25
 */
public class SigninToken extends UsernamePasswordToken implements java.io.Serializable{
    private static final long serialVersionUID = -6451794657814516274L;

    public SigninToken (String username, String pswd) {
        super(username,pswd);
        this.pswd = pswd ;
    }



    private String pswd ;

    public String getPswd() {
        return pswd;
    }


    public void setPswd(String pswd) {
        this.pswd = pswd;
    }


}
