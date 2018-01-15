package com.soauth.server;

import com.soauth.core.utils.SpringBeanUtils;
import com.soauth.server.oauth.SoauthAuthRequest;
import com.soauth.server.oauth.authorize.AuthzCodeHandler;
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
import java.util.UUID;

/**
 * grant_type=' '
 * 测试验证器流程.
 *
 */
@Test
@ContextConfiguration(locations = "classpath:content.xml", initializers = {TestApplicationContextInitializer.class} )
public class ValidatorAuthorizationCodeAccessTokenTest extends AbstractTransactionalTestNGSpringContextTests {

    /**
     * 此方法用于构建一个client端的有效的code请求(Oauth2 || OIDC), 并返回一个模拟实例
     *
     * @return
     */
    public HttpServletRequest builderRequest(String response_type){
        HttpServletRequest servletRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(servletRequest.getParameter("uri")).thenReturn("http://example.com");
        Mockito.when(servletRequest.getParameter("state")).thenReturn("test");
        Mockito.when(servletRequest.getParameter("redirect_uri")).thenReturn("http://localhost:8089/client/oidc/authorize_callback_code");
        Mockito.when(servletRequest.getParameter("response_type")).thenReturn(response_type);
        Mockito.when(servletRequest.getParameter("client_secret")).thenReturn("test");
        Mockito.when(servletRequest.getParameter("client_id")).thenReturn("testclient");
        Mockito.when(servletRequest.getParameter("scope")).thenReturn("openid refreshToken");

        Mockito.when(servletRequest.getMethod()).thenReturn("POST");
        Mockito.when(servletRequest.getContentType()).thenReturn("application/x-www-form-urlencoded");
        return  servletRequest;
    }




    @BeforeTransaction
    public void before() throws Exception {
       SpringBeanUtils.initialize(applicationContext);
    }

    public HttpServletResponse builderResponse() throws IOException {

        HttpServletResponse response=Mockito.mock(HttpServletResponse.class);
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(String.valueOf(new ClassPathResource("testMessage.txt"))));
        return  response;
    }

    /**
     * response_type='code'
     *
     * 测试 client 发送 authorization_code 请求, 并执行验证测试, 测试完成后,会在项目目录下(idea)生成名为 class path resource[testMessage.txt] 文件
     *
     * 错误示范:
     * error: {"error_description":"Invalid scope '[openid,refreshToken]'","error":"invalid_scope"}
     *
     * 正确示范:
     *  文件无内容,回调到Client接口
     *
     * @throws OAuthProblemException
     * @throws OAuthSystemException
     */
    @Test
    public void  authorizationCodeValidatorTest() throws OAuthProblemException, OAuthSystemException, IOException {
          HttpServletRequest request=  builderRequest("code");
          HttpServletResponse response = builderResponse();
        SoauthAuthRequest authRequest = new SoauthAuthRequest(request);
        AuthzCodeHandler authzCodeHandler = new AuthzCodeHandler (authRequest,response);
        authzCodeHandler.verify();
    }

    @Test
    public void  response() throws IOException {

        HttpServletResponse response = builderResponse();
        response.sendRedirect("http://localhost:8089/client/oidc/authorize_callback_code?code=2c65c0a2-6ac2-380a-98f8-ce20e2a150e1&state=test");
    }






}
