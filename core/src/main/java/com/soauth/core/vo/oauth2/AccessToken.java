package com.soauth.core.vo.oauth2;


import com.google.common.collect.Maps;
import com.soauth.core.utils.DateUtils;
import com.soauth.core.vo.AbstarctVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.jose4j.jwt.JwtClaims;

import java.util.Map;
import java.util.Set;


/**
 *
 * token 对象
 * @author zhoujie
 */

@EqualsAndHashCode
public class AccessToken extends AbstarctVo{


    private static final long serialVersionUID = -3065517831372557033L;
    public static final String BEARER_TYPE = "Bearer";


    /**
     * access_token 过期时间
     */
    public static final int ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60 * 12;


    protected static long THOUSAND = 1000L;


    private static final String ID_TOEN=AbstractOIDC.ID_TOKEN;

    /**
     * 如果用户是请求认证的话,将idToken存在这里
     */
    @Getter
    private Map<String,Object> idMap= Maps.newHashMap();
    @Getter
    @Setter
    private Long  id;

    @Getter
    @Setter
    private String tokenvalue;

    @Getter
    @Setter
    private  ClientDetails clientDetails;


    @Getter
    @Setter
    private Set<String> scope;

    @Getter
    @Setter
    private String tokenType = BEARER_TYPE;

    public int tokenExpiredSeconds = ACCESS_TOKEN_VALIDITY_SECONDS;

    @Getter
    @Setter
    private String permissions;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private RefreshToken refreshToken;

    public AccessToken() {

    }


    public boolean tokenExpired() {
        final long time = createTime.getTime() + (this.tokenExpiredSeconds * THOUSAND);
        return time < DateUtils.date().getTime();
    }

    public void setIdToken(String idToken){

        idMap.put(ID_TOEN,idToken);
    }

    public long currentTokenExpiredSeconds() {
        if (tokenExpired()) {
            return -1;
        }
        final long time = createTime.getTime() + (this.tokenExpiredSeconds * THOUSAND);
        return (time - DateUtils.date().getTime()) / THOUSAND;
    }


}
