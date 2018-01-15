package com.soauth.server;

import com.soauth.core.utils.SpringBeanUtils;
import com.soauth.core.vo.oauth2.AccessToken;
import com.soauth.core.vo.oauth2.ClientDetails;
import com.soauth.server.oauth.SoauthTokenRequest;
import com.soauth.server.oauth.token.TokenDispatcher;
import com.soauth.server.service.business.AuthzCodeAccessToken;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 测试token生成
 */


@Test
@ContextConfiguration(locations = "classpath:content.xml" , initializers = {TestApplicationContextInitializer.class})
public class ConnectTokenBuilderTest extends AbstractTransactionalTestNGSpringContextTests {



    /**
     * 此方法用于构建一个client端的有效的code请求(Oauth2 || OIDC), 并返回一个模拟实例
     *
     * @return
     */
    public HttpServletRequest  validBuilderRequest(){
        HttpServletRequest servletRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(servletRequest.getParameter("uri")).thenReturn("http://example.com");
        Mockito.when(servletRequest.getParameter("state")).thenReturn("test");
        Mockito.when(servletRequest.getParameter("redirect_uri")).thenReturn("http://example.com");
        Mockito.when(servletRequest.getParameter("grant_type")).thenReturn("authorization_code");
        Mockito.when(servletRequest.getParameter("client_secret")).thenReturn("test");
        Mockito.when(servletRequest.getParameter("client_id")).thenReturn("testclient");
        Mockito.when(servletRequest.getParameter("scope")).thenReturn("openid,refreshToken");
        Mockito.when(servletRequest.getMethod()).thenReturn("POST");
        Mockito.when(servletRequest.getContentType()).thenReturn("application/x-www-form-urlencoded");
        return  servletRequest;
    }


    /**
     * 此方法用于构建一个client端的有效的Token请求(Oauth2 || OIDC), 并返回一个模拟实例
     *
     * @return
     */
    public HttpServletRequest  validBuilderTokenRequest(String code){
        HttpServletRequest servletRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(servletRequest.getHeader("")).thenReturn("http://example.com");
        Mockito.when(servletRequest.getParameter("state")).thenReturn("test");
        Mockito.when(servletRequest.getParameter("redirect_uri")).thenReturn("http://localhost:8089/client/oidc/authorize_callback_code");
        Mockito.when(servletRequest.getParameter("grant_type")).thenReturn("authorization_code");
        Mockito.when(servletRequest.getParameter("client_secret")).thenReturn("test_secret");
        Mockito.when(servletRequest.getParameter("client_id")).thenReturn("testclient");
        Mockito.when(servletRequest.getParameter("code")).thenReturn(code);
        Mockito.when(servletRequest.getParameter("display")).thenReturn("wap");
        Mockito.when(servletRequest.getParameter("scope")).thenReturn("openid  refresh_token");
        Mockito.when(servletRequest.getMethod()).thenReturn("POST");
        Mockito.when(servletRequest.getContentType()).thenReturn("application/x-www-form-urlencoded");
        return  servletRequest;
    }

    public HttpServletResponse builderResponse() throws IOException {

        HttpServletResponse response=Mockito.mock(HttpServletResponse.class);
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(String.valueOf(new ClassPathResource("tokenMessage.txt"))));
        return  response;
    }
    @BeforeTransaction
    public void before() throws Exception {
       SpringBeanUtils.initialize(applicationContext);
    }
    /**
     * 此方法用于测试Token生成
     * @throws OAuthProblemException
     * @throws OAuthSystemException
     */
    @Test
    public void  testCreateAccessToken() throws OAuthProblemException, OAuthSystemException {
        HttpServletRequest servletRequestrequest =validBuilderRequest();
        System.out.println("uri................"+servletRequestrequest.getParameter("uri"));
        ClientDetails clientDetails = new ClientDetails();
        clientDetails.setClientId("test");
        String code="1234";
       SoauthTokenRequest request = new SoauthTokenRequest(servletRequestrequest);
        AuthzCodeAccessToken accessToken= new AuthzCodeAccessToken();
        AccessToken accessToken1= accessToken.createNewAccessToken(clientDetails,code,request);

        System.out.println(accessToken1.getTokenvalue());

    }


    @Test
    public  void getToken(){
        HttpServletRequest servletRequestrequest =validBuilderTokenRequest("a91748c8-eb9b-3c39-87a3-eec29a04d8ea ");
        try {
            SoauthTokenRequest request = new SoauthTokenRequest(servletRequestrequest);
            HttpServletResponse response =builderResponse();
            TokenDispatcher dispatcher = new TokenDispatcher(response,request);
            dispatcher.doDispatch();

        } catch (OAuthSystemException e) {
            e.printStackTrace();
        } catch (OAuthProblemException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
