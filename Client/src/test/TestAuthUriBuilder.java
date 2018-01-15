import com.soauth.api.client.model.ServerConfig;
import com.soauth.api.client.openid.connect.service.business.AuthReuqestUrlBuilder;
import com.soauth.api.client.openid.connect.service.impl.ConnectAuthEndpointServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * 测试RP到OP端点的连接状态
 */
public class TestAuthUriBuilder {

    /**
     *  第三方应用提交请求参数 同partylogin.js
     *
     * @return
     */
    public HttpServletRequest builderRequest(){
        HttpServletRequest servletRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(servletRequest.getParameter("redirect_uri")).thenReturn("http://localhost:8089/client/oidc/authorize_callback_code");
        Mockito.when(servletRequest.getParameter("grant_type")).thenReturn("authorization_code");
        Mockito.when(servletRequest.getParameter("client_secret")).thenReturn("test_secret");
        Mockito.when(servletRequest.getParameter("client_id")).thenReturn("testclient");
        Mockito.when(servletRequest.getParameter("scope")).thenReturn("openid  refresh_token");
        Mockito.when(servletRequest.getMethod()).thenReturn("GET");
        Mockito.when(servletRequest.getContentType()).thenReturn("application/x-www-form-urlencoded");
        return  servletRequest;
    }

    @Test
    public void testClientRequest(){
        HttpServletRequest request = builderRequest();

    }
    @Test
    public  void connectbuilderUri(){
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setAuthorizationEndpointUri("http://localhost:8000/SoauthServer/openid/authorize");

        String test="test";
        AuthReuqestUrlBuilder urlBuilder= new AuthReuqestUrlBuilder();
        Map map= Collections.emptyMap();

        String uri=urlBuilder.authbuilderUrl(serverConfig,test,test,test,test,test,test,test,map);

        System.out.println(uri);
    }



    @Test
    public  void testStringUtils(){
        Set<String> scope= new LinkedHashSet<>();
        scope.add("openid");
        scope.add("refreshToken");
        String test=StringUtils.join(scope," ");

        System.out.println(test);

    }




}
