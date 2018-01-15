package com.soauth.core.utils;

import com.soauth.core.openid.connect.PrivateKeyFactory;
import com.soauth.core.openid.connect.PublicKeyFactory;
import org.jose4j.keys.RsaKeyUtil;
import org.springframework.core.io.ClassPathResource;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 *
 * @author  周杰
 * @date 2017/12/1
 *
 * 获取public / private key
 */
public class RsaKeyUtils extends RsaKeyUtil{


    public static PrivateKey getPrivateKey(boolean singleton, String filePath) throws Exception {

        final PrivateKeyFactory factory= new PrivateKeyFactory();
        factory.setAlgorithm(RsaKeyUtil.RSA);
        factory.setResource(new ClassPathResource(filePath));
        factory.setSingleton(singleton);

            return factory.getObject();

    }

   public static PublicKey getPublicKey(boolean singleton, String filePath) throws Exception {

            final PublicKeyFactory factory = new PublicKeyFactory();
            factory.setAlgorithm(RsaKeyUtil.RSA);
            factory.setResource(new ClassPathResource(filePath));
            factory.setSingleton(singleton);
            return factory.getObject();

    }

    /**
     *
     * 解密RSA
     * @param encode 解密的字符串
     * @param privateKey 秘钥
     * @param algname RSA
     * @return
     * @throws Exception
     */
    public static String decryption(String encode, PrivateKey privateKey , String algname) throws  Exception{

        BASE64Decoder decoder = new BASE64Decoder();

        byte[] bs;

        bs=decoder.decodeBuffer(encode);
        Cipher cipher =Cipher.getInstance(algname);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

       return new String(cipher.doFinal(bs));
    }
}
