package com.soauth.server.oauth.validator.client;

import com.soauth.core.vo.oauth2.AccessToken;
import com.soauth.core.vo.oauth2.ClientDetails;
import com.soauth.server.oauth.SoauthTokenRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author zhoujie
 * @date 2017/10/12
 *validate grantType, clientSecret, refreshToken
 */
public class RefreshTokenClientValidator extends BaseTokenClientValidator {

   private  final  static Logger log= LoggerFactory.getLogger(RefreshTokenClientValidator.class);

    public RefreshTokenClientValidator(SoauthTokenRequest oAuthRequest) {

        super(oAuthRequest);
    }

    @Override
    protected OAuthResponse validateSelf(ClientDetails clientDetails) throws OAuthSystemException {
        final  String grantType=grentType();
        // verify grantType
        if(!clientDetails.getGrantTypes().contains(grantType)){
            log.debug("ClientCredent grantType{} 无效",grantType);
            invalidGrantTypeResponse(grantType);
        }

        //verify clientSecret
        final String clientSecret=oauthRequest.getClientSecret();
        if(clientSecret == null ||!clientSecret.equals(clientDetails.getClientSecret())){
            log.debug("clientSecret 无效{}",clientSecret);
            return  invalidClientSecretResponse();
        }

        // verify refreshToken
        final  String refreshToken= tokenRequest.getRefreshToken();

        AccessToken accessToken=oauth2Service.findrefreshToken(refreshToken,oauthRequest.getClientId());


        return null;
    }
}
