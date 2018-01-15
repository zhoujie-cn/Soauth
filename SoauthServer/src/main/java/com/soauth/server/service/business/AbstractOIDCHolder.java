package com.soauth.server.service.business;

import com.soauth.core.utils.SpringBeanUtils;
import com.soauth.server.service.TokenService;

/**
 *
 * @author zhoujie
 * @date 2017/12/2
 */
public  abstract  class AbstractOIDCHolder {

   protected   transient TokenService  tokenService= SpringBeanUtils.getBean(TokenService.class);

   TokenBuilder  tokenBuilder=SpringBeanUtils.getBean(TokenBuilder.class);

  // protected   ServerConfig serverConfig=SpringBeanUtils.getBean(ServerConfig.class);

   protected  String currentUser(){
      return  "testuser";//UserManager.currentUser().getUsername();
   }

}
