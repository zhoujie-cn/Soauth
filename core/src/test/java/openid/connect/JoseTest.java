package openid.connect;

import com.soauth.core.openid.connect.JwksCacheService;
import com.soauth.core.openid.connect.PrivateKeyFactory;
import com.soauth.core.openid.connect.PublicKeyFactory;
import com.soauth.core.openid.connect.encryption.DefaultJwtencryptAnddecryption;
import com.soauth.core.openid.connect.signature.DefaultSignatrueAndverifySignatrue;
import com.soauth.core.vo.oauth2.AccessToken;
import org.apache.commons.codec.binary.Base64;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.jwk.*;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.EllipticCurves;
import org.jose4j.keys.RsaKeyUtil;
import org.jose4j.lang.JoseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.testng.annotations.Test;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by zhoujie on 2017/11/5.
 */
public class JoseTest {

    private JwtClaims jwtClaims(){
        JwtClaims claims= new JwtClaims();
        claims.setIssuer("Issuer"); //主体
        claims.setAudience("Audience"); //发送给谁
        claims.setExpirationTimeMinutesInTheFuture(10); //过期时间 单位分钟
        claims.setGeneratedJwtId(); //令牌唯一标识
        claims.setIssuedAtToNow(); //令牌创建时间
        claims.setNotBeforeMinutesInThePast(2); //在此时间之前，令牌无效, 单位分钟
        claims.setSubject("subject"); //内容
        claims.setClaim("email","example.com"); //附加声明

        return  claims;
    }


    @Test
    public  void  testsignatrueJwt(){
      PrivateKey pk=   getPrivateKey();

        DefaultSignatrueAndverifySignatrue signatrue = new DefaultSignatrueAndverifySignatrue();
        JwtClaims claims= jwtClaims();

        try {
            signatrue.signatrueJwt(pk,claims.toString());
        } catch (JoseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public  void PublicAndPrivateKey(){

        try {
           RsaJsonWebKey jwk= RsaJwkGenerator.generateJwk(1024);

          Key key= jwk.getRsaPrivateKey();
          System.out.print(key.getFormat());


        } catch (JoseException e) {
            e.printStackTrace();
        }
    }
/*
     @Test
     public  String encryptTest(){
         final JwtClaims jwtClaims=jwtClaims();
         DefaultJwtencryptAnddecryption encrypt= new DefaultJwtencryptAnddecryption();

       String encrpy=  encrypt.encryptJwt(jwtClaims.toJson());

       System.out.print(encrpy);
       return encrpy;
     }

     @Test
     public  void decryptTest(){
         String decrypt=encryptTest();
         DefaultJwtencryptAnddecryption verfiydecrypt= new DefaultJwtencryptAnddecryption();

         String token =verfiydecrypt.decryptJwt(decrypt);
         System.out.print("token  "+token);
     }
*/



 private static PrivateKey getPrivateKey(){

     final PrivateKeyFactory factory= new PrivateKeyFactory();
     factory.setAlgorithm(RsaKeyUtil.RSA);
     factory.setResource(new ClassPathResource("keys/RSA2048private.key"));
     factory.setSingleton(false);

     try {
         return factory.getObject();
     } catch (Exception e) {
         e.printStackTrace();
     }
     return  null;
 }

    private static PublicKey getPublicKey() {
        try {
            final PublicKeyFactory factory = new PublicKeyFactory();
            factory.setAlgorithm(RsaKeyUtil.RSA);
            factory.setResource(new ClassPathResource("keys/RSA2048public.key"));
            factory.setSingleton(false);

            return factory.getObject();
        } catch (final Exception e) {
            e.printStackTrace();
            return  null;
        }
    }

    /**
     * 解密登录页面传回来的数据
     * @throws Exception
     */
    @Test
    public  void testJsencrypt() throws Exception {
        BASE64Decoder decoder = new BASE64Decoder();

        String encode="GyBX5aKRINwC3Vb1QqKbXDVaWc4bzgjZRzhHHaXb8VYc7rcL2ZYZDRyG4CJJUgGcayj9Js4u2BUH+wGeRMmfmNd+KpFsJ+0MShBhPAiX48SCRHxFkB+RN3S8aQcyye0joju2Uxrp/FkbTj9QwTWLH/0PEs8YNMy5wuyhqC1d4uqvMNGP1eq6Wt8ODbBIRSXjQkw/i8DO2u0ekMs3CH8ZgNpbU4gH3Ff7ReqicyGrc07cI5erdjYYuGwAFQ7nGpnvNOP2d3zT6XLz5Nv3iZ5X590TmaotUuD4VG5nYgW4XvuiIZCgmtTjHvk2abnH7bIKrg3vPBwCnamARPuxlcdM2w==";
        byte[] bs= null;

        bs=decoder.decodeBuffer(encode);
        Cipher cipher =Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE,getPrivateKey());

        System.out.println(new String(cipher.doFinal(bs)));
    }


    @Test
    public  void testExpired(){
        AccessToken accessToken = new AccessToken();
        accessToken.tokenExpired();

    }

    @Test
 public void encryptRsaTest(){
        PrivateKey pk =getPrivateKey();
     RSAPublicKey jwk= (RSAPublicKey) getPublicKey();


        BASE64Encoder bse=new BASE64Encoder();
        System.out.println(jwk.getEncoded());

     JsonWebKey rsa= new RsaJsonWebKey(jwk);

     rsa.setAlgorithm("RSA");
     rsa.setKeyId("publibkey");
     //System.out.print("public key to json:   "+rsa.getKey());
     JsonWebKey jwt=null;
     try {
        jwt=  JsonWebKey.Factory.newJwk(rsa.toJson());
     } catch (JoseException e) {
         e.printStackTrace();
     }
     //JsonWebKey jwks=rsa;
     DefaultJwtencryptAnddecryption verfiydecrypt= new DefaultJwtencryptAnddecryption();

     final JwtClaims jwtClaims=jwtClaims();

     String idtoken= null;
     try {
         idtoken = verfiydecrypt.encryptJwtAsrsa256(jwt,jwtClaims.toJson());

         verfiydecrypt.decryptJwt(pk,idtoken);
     } catch (JoseException e) {
         e.printStackTrace();
     }

 }

    public static byte[] decodeBase64(final String data) {
        return Base64.decodeBase64(data);
    }


    @Test
    public void jwtConsumer()throws  Exception{
        //用于在JWK中对JWT进行签名和验证
        EllipticCurveJsonWebKey sendJwk= EcJwkGenerator.generateJwk(EllipticCurves.P256);
        //用于对JWT进行加密和解密.
        EllipticCurveJsonWebKey receiverJwk= EcJwkGenerator.generateJwk(EllipticCurves.P256);
        sendJwk.setKeyId("sha1");


        final  String publicKey=sendJwk.toJson(JsonWebKey.OutputControlLevel.PUBLIC_ONLY);
        final  String privateKey=sendJwk.toJson(JsonWebKey.OutputControlLevel.INCLUDE_PRIVATE);

        System.out.println("public key"+publicKey);
        System.out.println("private key"+privateKey);

        //生成id token
        final JwtClaims jwtClaims=jwtClaims();
        JsonWebSignature jws= new JsonWebSignature();
        jws.setPayload(jwtClaims.toJson());
        jws.setKey(sendJwk.getPrivateKey()); //用PrivateKey 签名
        jws.setKeyIdHeaderValue(sendJwk.getKeyId());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.ECDSA_USING_P256_CURVE_AND_SHA256);

        //getCompactSerialization() 会自动Base64编码
        String innerIdToken = jws.getCompactSerialization();
        System.out.println("签名后的 Id Token    "+innerIdToken);



        //对idToken进行加密
        JsonWebEncryption jwe= new JsonWebEncryption();
         //加密一个随机生成的内容加密密钥
        jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.ECDH_ES_A128KW);
        String alg= ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256;
        jwe.setEncryptionMethodHeaderParameter(alg);

        receiverJwk.setKeyId("sha1");
        jwe.setKey(receiverJwk.getPublicKey());
        jwe.setKeyIdHeaderValue(receiverJwk.getKeyId());

        jwe.setContentTypeHeaderValue("jwt");
        jwe.setPayload(innerIdToken);
        String idtoken=jwe.getCompactSerialization();

        System.out.println("headers"+jwe);
        System.out.println("加密后的 idtoken"+idtoken);

        //用于解密idtoken, 并验签
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime()
                .setRequireSubject()
                .setExpectedIssuer("Issuer")
                .setExpectedAudience("Audience")
                //解密的私钥
                .setDecryptionKey(receiverJwk.getPrivateKey())
                //验签的公钥
                .setVerificationKey(sendJwk.getPublicKey())
                .build(); // create the JwtConsumer instance

        //验证jwt并且赋值Claims,内容不一致抛出InvalidJwtException
        try{
        JwtClaims jwtClaims1=jwtConsumer.processToClaims(idtoken);

        System.out.println("验证jwt成功!"+jwtClaims1.toString());

        /*
            NumericDate expirationTime=jwtClaims1.getExpirationTime();
            if(expirationTime.isBefore(NumericDate.now())){
                throw new InvalidJwtException("过期的jwt");
            }*/
    }catch (InvalidJwtException e){

            System.out.println("无效的jwt ");
        }
    }
}
