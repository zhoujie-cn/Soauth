package com.soauth.server.oauth.validator.client;

import com.soauth.core.vo.oauth2.ClientDetails;
import com.soauth.server.oauth.SoauthAuthRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 *
 * @author zhoujie
 * @date 2017/10/12
 *
 * client获取Code时,执行验证
 */
public class CodeClientValidator extends BaseClientValidator {
    private final  static Logger log= LoggerFactory.getLogger(CodeClientValidator.class);



    public CodeClientValidator(SoauthAuthRequest oAuthRequest) {
        super(oAuthRequest);
    }

    @Override
    protected OAuthResponse validateSelf(ClientDetails clientDetails) throws OAuthSystemException {

        //verify  state
        final String state = getState();
        if (StringUtils.isEmpty(state)) {
            log.info("state参数为空{}",state);
            return invalidStateResponse();
        }

        //verify  scope
        final Set<String> scopes = oauthRequest.getScopes();
        if (scopes.isEmpty() || verifyScopes(scopes, clientDetails)) {
            log.info("scope参数为空{}",state);
            return invalidScopeResponse();
        }

        //verify  redirect_uri
        final String redirecturi=oauthRequest.getRedirectURI();

        log.info("reqeust uri={}",redirecturi);
        if(redirecturi==null || !redirecturi.equals(clientDetails.getRedirectUri())){
            log.info("redirect_uri 无效{}",redirecturi);
            return invalidRedirectUriResponse();
        }
        log.info("success CodeClientValidator");
        return null;
    }

    private String getState() {
        return ((OAuthAuthzRequest) oauthRequest).getState();
    }
}
