package com.soauth.core.vo.oauth2;


import com.soauth.core.vo.AbstarctVo;
import lombok.Data;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.jose4j.jwk.JsonWebKey;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 *client 请求对象类型
 */
@Data
public class ClientDetails extends AbstarctVo {


    private static final long serialVersionUID = -3267897492642972969L;

    private String resourceIds;

    private Set<String> scope = new LinkedHashSet<>();

    private String scopeString;

    private  boolean trusted;

    private Set<String> grantTypes= new LinkedHashSet<>();

    private String grantType;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private String name;

    private String clientId;

    private String clientSecret;

    private String clientName;

    private String redirectUri;

    private String clientUri;

    private String description;

    private String loginUri;

    /**
     * 访问设备类型
     */
    private String appType;

    private  String jwksUri;

    /**
     *
      */
    Authentication tokenEndpointmethod=Authentication.SECRET_BASIC;
    /**
     * public key
     */
    private JsonWebKey jwk;

    public ClientDetails() {
    }

    /**
     * document: http://openid.net/specs/openid-connect-core-1_0.html#ClientAuthentication
     */
    public enum Authentication {
        SECRET_POST("client_secret_post"),
        SECRET_BASIC("client_secret_basic"),
        SECRET_JWT("client_secret_jwt"),
        PRIVATE_KEY("private_key_jwt"),
        NONE("none");

        private final String value;

        // map to aid reverse lookup
        private static final Map<String, Authentication> lookup = new HashMap<>();
        static {
            for (Authentication a : Authentication.values()) {
                lookup.put(a.getValue(), a);
            }
        }

        Authentication(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Authentication getByValue(String value) {
            return lookup.get(value);
        }
    }



    public boolean supportRefreshToken() {
        return this.grantTypes != null && this.grantTypes.contains(GrantType.REFRESH_TOKEN.toString());
    }
}
