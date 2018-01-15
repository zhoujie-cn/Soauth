package com.soauth.core.openid.connect;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.core.io.Resource;
import sun.misc.BASE64Decoder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 *
 * @author zhouejie
 * @date 2017/11/15
 *
 * 从资源文件中读取public key文件
 */
public class PublicKeyFactory  extends AbstractFactoryBean<PublicKey>{

    private  static final Logger log= LoggerFactory.getLogger(PublicKey.class);

    @Getter
    @Setter
    private Resource resource;


    @Getter
    @Setter
    private  String algorithm;

    @Override
    public Class<?> getObjectType() {
        return PublicKey.class;
    }

    @Override
    protected PublicKey createInstance() throws Exception {
        log.debug("创建公钥实例");


        InputStream input=this.resource.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(input));

        BASE64Decoder base64decoder = new BASE64Decoder();

        String s = br.readLine();
        StringBuffer publickey = new StringBuffer();
        s = br.readLine();

        while (s.charAt(0) != '-') {
            publickey.append(s + "\r");
            s = br.readLine();
        }

        byte[] keybyte = base64decoder.decodeBuffer(publickey.toString());

        KeyFactory kf = KeyFactory.getInstance(this.algorithm);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keybyte);
        PublicKey publicKey = kf.generatePublic(keySpec);

        return  publicKey;
    }
}
