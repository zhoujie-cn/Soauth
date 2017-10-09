package com.soauth.core.cache;

/**
 * Created by zhoujie on 2017/9/25.
 */
public class ConstantsCache {



    /**
     * AccessToken  cache
     * key:  tokenId
     */
    public static final String ACCESS_TOKEN_CACHE = "accessTokenCache";


    /**
     * ClientDetails  cache
     * key:  clientId + cache_name
     */
    public static final String CLIENT_DETAILS_CACHE = "clientDetailsCache";


    /**
     * User  cache
     */
    public static final String USERS_CACHE = "usersCache";


    /**
     * OauthCode  cache
     * key:  code + clientId
     */
    public static final String OAUTH_CODE_CACHE = "oauthCodeCache";

    private  ConstantsCache(){

    }
}
