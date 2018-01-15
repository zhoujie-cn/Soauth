package com.soauth.core.openid.connect;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.soauth.core.openid.connect.encryption.DefaultJwtencryptAnddecryption;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author  zhoujie
 * @date 2017/11/17
 * 从服务器端获取public key,并缓存
 *
 * 这个类通常用于Client端加密和验签
 */
public class JwksCacheService{
    private static  final Logger log = LoggerFactory.getLogger(JwksCacheService.class);
    LoadingCache<String,Object> jwkSet;


     public  JwksCacheService(){
        jwkSet= CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS  )
                .maximumSize(100)
                .build(new InitJwkSet(HttpClientBuilder.create().useSystemProperties().build()));
     }


     public Object getPublicKey(String jwk_uri){
         try {
            return jwkSet.get(jwk_uri);
         } catch (ExecutionException e) {
             log.warn("获取公钥失败.");
             e.printStackTrace();
         }

         return null;
     }
    /**
     *  cache 实现类. 使用Apache作为HttpClient的实现. 使用Guava作为作为具体对象缓存
     */
    private class InitJwkSet extends CacheLoader<String,Object> {
        /**
         *  初始化一个HttpClient
         */
        private HttpComponentsClientHttpRequestFactory httpclientFactory;
        /**
         * 从服务器端获取jwks
         */
        private RestTemplate rest;

          private InitJwkSet(HttpClient client) {
              this.httpclientFactory = new HttpComponentsClientHttpRequestFactory(client);
              this.rest = new RestTemplate(httpclientFactory);

          }

          @Override
          public Object load(String jwk_uri) throws Exception {
              log.debug("从服务端加载公钥");
              return rest.getForObject(jwk_uri, String.class);
          }
      }


}
