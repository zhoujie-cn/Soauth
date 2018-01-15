package com.soauth.core.openid.connect;

import lombok.Getter;
import lombok.Setter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.core.io.Resource;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 *
 * @author  zhoujie
 * @date 2017/11/15
 *
 * 从文件中读取私钥
 */
public class PrivateKeyFactory extends AbstractFactoryBean<PrivateKey> {

    private static  final Logger log = LoggerFactory.getLogger(PrivateKeyFactory.class);

    @Setter
    @Getter
    private Resource resource;

    @Getter
    @Setter
    private String algorithm;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Override
    public Class<?> getObjectType() {
        return PrivateKey.class;
    }

    @Override
    protected PrivateKey createInstance() throws Exception {
        log.debug("创建PrivateKey实例");
        PrivateKey key = readPemKey();
        if(key==null){
            key=readDERPrivateKey();
        }

        return key;
    }

    protected PrivateKey readPemKey(){
         log.debug("读取pem Private key ");
        try (Reader in = new InputStreamReader(this.resource.getInputStream(), StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(in)) {
            final  PEMParser pp = new PEMParser(br);
            final PEMKeyPair pemKeyPair = (PEMKeyPair) pp.readObject();
            final KeyPair kp = new JcaPEMKeyConverter().getKeyPair(pemKeyPair);
            return kp.getPrivate();
        } catch (final Exception e) {
            log.debug("Unable to read key", e);
            return null;
        }
    }


    private PrivateKey readDERPrivateKey() {
        log.debug("Attempting to read key as DER [{}]", this.resource);
        try (InputStream privKey = this.resource.getInputStream()) {
            final byte[] bytes = new byte[privKey.available()];
            privKey.read(bytes);
            final PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(bytes);
            final KeyFactory factory = KeyFactory.getInstance(this.algorithm);
            return factory.generatePrivate(privSpec);
        } catch (final Exception e) {
            log.debug("Unable to read key", e);
            return null;
        }
    }

}
