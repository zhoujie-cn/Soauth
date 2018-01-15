package com.soauth.core.openid.connect.signature;

import org.apache.commons.lang.RandomStringUtils;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.lang.JoseException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.PrivateKey;

/**
 *
 * @author zhoujie
 * @date 2017/11/14
 *
 * 进行RSA签名
 */
public class DefaultSignatrueAndverifySignatrue {

    public String signatrueJwt(final PrivateKey key, final String payload) throws JoseException {

        String kid= RandomStringUtils.random(6);
        JsonWebSignature jws= new JsonWebSignature();
        jws.setPayload(payload);
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA512);
        jws.setKey(key);
        jws.setKeyIdHeaderValue(kid);

        return jws.getCompactSerialization();
    }


    public  String verifyJwtSignature(final Key signingKey, final String signpayload) throws JoseException {

        final JsonWebSignature jws= new JsonWebSignature();

            jws.setCompactSerialization(signpayload);
            jws.setKey(signingKey);
            final boolean verified= jws.verifySignature();

            if (verified){
                final String payload=jws.getPayload();
                return  payload;
            }

        return  null;
    }
}
