package com.soauth.server.service.business;

import com.soauth.core.utils.DateUtils;
import com.soauth.core.vo.oauth2.ClientDetails;
import com.soauth.core.vo.oauth2.Oauth2Code;
import org.apache.oltu.oauth2.as.issuer.UUIDValueGenerator;
import org.apache.oltu.oauth2.as.issuer.ValueGenerator;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.UUID;

/**
 *
 * @author zhoujie
 * @date 2017/12/1
 *
 */
public class AuthDeviceCode extends  AbstractOIDCHolder {

    private static Logger logger = LoggerFactory.getLogger(AuthDeviceCode.class);

    private ClientDetails clientDetails;


    public  AuthDeviceCode(ClientDetails clientDetails){
        this.clientDetails=clientDetails;
    }

    public  String createAndSaveCode(){
      logger.info("create And Save Code");
        String clientid=clientDetails.getClientId();
        String username= currentUser();

        Oauth2Code code= tokenService.findOauth2CodeUserAndClientId(username,clientid);

       //如果有删除原来旧的code
      if(code!=null){
          tokenService.deleteOauth2Code(code);
      }

      String authCode =generateValue();

      Date createTime=new Date();

      //保存创建的code信息,code有效时间为30分钟
      Oauth2Code oauth2Code = new Oauth2Code().code(authCode).username(username).clientId(clientid);

      oauth2Code.createtime(DateUtils.getStringDate(createTime));
      oauth2Code.explration(DateUtils.getStringDate(new Date(createTime.getTime()+Oauth2Code.DEFAULT_CODE_EXPIRATION)));

      tokenService.saveOauth2Code(oauth2Code);

        return  oauth2Code.code();
    }
    public  String generateValue(){
        return generateValue(UUID.randomUUID().toString());

    }

    public String generateValue(String param) {
        return UUID.fromString(UUID.nameUUIDFromBytes(param.getBytes()).toString()).toString();
    }

}
