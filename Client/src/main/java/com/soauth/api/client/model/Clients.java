package com.soauth.api.client.model;

import lombok.Data;

/**
 *
 * @author zhoujie
 * @date 2017/11/16
 *
 * 接收第三方登录参数,以及发送给认证服务端的参数
 */
@Data
public class Clients {

     private String clientid;

     private String responseType;

     private String redirectUri;

     private String scope;

     private String state;

     private String display;

     /**
      * 防止重放攻击
      */
     private  String nonce;

}
