package com.soauth.server;

import org.apache.commons.lang.StringUtils;
import org.apache.oltu.oauth2.common.OAuth;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

/**
 * 测试userinfo端点相关代码
 */

@Test
@ContextConfiguration(locations = "classpath:content.xml" , initializers = {TestApplicationContextInitializer.class})
public class TestUserInfo extends AbstractTransactionalTestNGSpringContextTests {

    public HttpServletRequest  validBuilderRequest(){
        HttpServletRequest servletRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(servletRequest.getHeader(OAuth.HeaderType.AUTHORIZATION)).thenReturn("bearer aasqzxdq-xx-xx");
        Mockito.when(servletRequest.getMethod()).thenReturn("GET");
        Mockito.when(servletRequest.getContentType()).thenReturn("application/x-www-form-urlencoded");
        return  servletRequest;
    }

    /**
     * 验证AcccessToken 提交方式
     */
    @Test
    private void getAccessToken(){
        HttpServletRequest request=validBuilderRequest();
        if(!StringUtils.isNotEmpty(request.getHeader(OAuth.HeaderType.AUTHORIZATION))){
            System.out.println("OK");
        }else if(!request.getMethod().equals(OAuth.HttpMethod.POST) && !request.getContentType().equals(OAuth.ContentType.URL_ENCODED)){
            System.out.println("OK");
        }else{
            System.out.println("ERROR");
        }
    }
}
