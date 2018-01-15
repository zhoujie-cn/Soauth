package com.soauth.api.client.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 *
 * @author zhoujie
 * @date 2017/11/1
 */
@Data
public class AuthenticationToken {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("id_token")
    private  String idToken;

    @JsonProperty("token_type")
    private  String tokenType;

    public  AuthenticationToken(){

    }



}
