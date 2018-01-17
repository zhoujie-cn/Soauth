package com.soauth.server;

import com.soauth.server.service.AdminService;
import com.soauth.server.service.UserInfoService;
import org.apache.commons.lang.StringUtils;
import org.apache.oltu.oauth2.common.OAuth;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 测试userinfo端点相关代码
 */

@Test
@ContextConfiguration(locations = "classpath:content.xml" , initializers = {TestApplicationContextInitializer.class})
public class TestUserInfo extends AbstractTransactionalTestNGSpringContextTests {

  @Autowired
    AdminService adminService;

    public HttpServletRequest  validBuilderRequest(){
        HttpServletRequest servletRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(servletRequest.getHeader(OAuth.HeaderType.AUTHORIZATION)).thenReturn("bearer aasqzxdq-xx-xx");
        Mockito.when(servletRequest.getMethod()).thenReturn("GET");
        Mockito.when(servletRequest.getContentType()).thenReturn("application/x-www-form-urlencoded");
        return  servletRequest;
    }

    public HttpServletResponse builderResponse(){
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        return  response;
    }


    @Test
    public void getUserSidebar(){
        Map<String, List<Object>> admin= adminService.adminSidebar(1l,validBuilderRequest(), builderResponse());
        System.out.println(admin.toString());
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
