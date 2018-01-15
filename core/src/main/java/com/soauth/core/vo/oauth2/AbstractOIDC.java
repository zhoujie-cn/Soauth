package com.soauth.core.vo.oauth2;

/**
 *
 * 系统常用字段
 * @author zhoujie
 */
public abstract class AbstractOIDC {


    public static final String REQUEST_USERNAME = "username";
    public static final String REQUEST_PASSWORD = "password";

    public static final String REQUEST_USER_OAUTH_APPROVAL = "user_oauth_approval";

    public static final String OAUTH_LOGIN_VIEW = "oauth_login";
    public static final String OAUTH_APPROVAL_VIEW = "oauth_approval";

    public  static final  String OAUTH_Implicit="implicit";
    /**
     * AuthRequest 常用字段 :http://openid.net/specs/openid-connect-core-1_0.html#AuthRequest
     *
     */
    public static final String OIDC_NONCE= "nonce";
    public static final String OIDC_LOGINHINT="login_hint";
    public static final String OIDC_IDTOKENHINT ="id_token_hint";
    public static final String OIDC_DISPLAY ="display";

    public  static final String ID_TOKEN="id_token";

    /**
     * SessionManger 常用字段: http://openid.net/specs/openid-connect-session-1_0.html
     */
    public static final String SESSION_STATE="session_state";
    public static final  String  POST_LOGOUT_REDIRECT_URI="post_logout_redirect_uri";
    public static final String  CHECK_SESSION_IFRAME="check_session_iframe";
    public static final String  END_SESSION_ENDPOINT="end_session_endpoint";

    /**
     * 以下字段用于请求jwtToken时使用.
     *
     */
     public static final String CLIENT_ASSERTION_TYPE="urn:ietf:params:oauth:client-assertion-type:jwt-bearer";
     public String CLIENT_ASSERTION=null;

    /**
     * 用于简化模式中
     */
    public  static final  String IMPLICIT_TOKEN="id_token%20token";

    public enum Display{
        PAGE("page"),
        POPUP("popup"),
        TOUCH("touch"),
        WAP("wap");

        private String parameterStyle;

        private Display(String parameterStyle) {
            this.parameterStyle = parameterStyle;
        }
        public String getDisplay(){
            return  parameterStyle;
        }
    }

    private AbstractOIDC() {
    }


}
