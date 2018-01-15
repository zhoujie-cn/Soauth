package com.soauth.core.vo.oauth2;

import com.soauth.core.utils.DateUtils;
import com.soauth.core.vo.AbstarctVo;
import lombok.Getter;
import lombok.Setter;
import org.jose4j.jwt.JwtClaims;

import static com.soauth.core.vo.oauth2.AccessToken.THOUSAND;

/**
 *
 * @author JZ9523
 * @date 2017/11/23
 */
public class RefreshToken extends AbstarctVo {

    /**
     * refresh_toen Default value default 30 days
     */
    public static final int REFRESH_TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 30;


     public int refreshTokenExpiredSeconds = REFRESH_TOKEN_VALIDITY_SECONDS;

    @Getter
    @Setter
    private String refreshvalue;

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private ClientDetails clientDetails;

   public boolean refreshTokenExpired() {
       final long time = createTime.getTime() + (this.refreshTokenExpiredSeconds * THOUSAND);
       return time < DateUtils.date().getTime();
   }

}
