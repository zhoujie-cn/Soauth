package com.soauth.api.client.web.authcontroller;

import com.soauth.api.client.openid.connect.service.AuthenticationTokenService;
import com.soauth.api.client.openid.connect.service.ConnectAuthEndpointService;
import com.soauth.core.vo.oauth2.AbstractOIDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author zhoujie
 * @date 2017/11/27
 */
@Controller
@RequestMapping(value = "oidc")
public class ImplicitControl {

    private Logger log= LoggerFactory.getLogger(ImplicitControl.class);

    @Autowired
    ConnectAuthEndpointService connectAuthEndPoint;

    @Autowired
    AuthenticationTokenService tokenService;

    /**
     * step 1:
     * 网站先发起第三方登录请求(模拟请求参数在partlogin.js下)
     *
     * step 2:
     * 获取当前请求登录的网站的信息
     *
     * step 3:
     * 组装当前请求登录的网站信息
     *
     * step 4:
     *向op发起认证授权请求
     *
     *
     */
    @RequestMapping(value = "implicit", method = RequestMethod.GET,consumes= MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void  signin(@RequestParam(value = "client_id") String clientid, @RequestParam(value = "scope") String scope,
                        @RequestParam(value = "redirect_uri") String redirectUri, @RequestParam(value = "response_type") String responseType,
                        @RequestParam(value = "client_secret") String clientsecret,
                        Device device, HttpServletRequest request, HttpServletResponse response){

        Map<String,String> options = new HashMap<>(3);

        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8000/SoauthServer/openid/authorize");
        /**
         * 服务器端弹出页面选项
         */
        if(device.isNormal()){
            options.put(AbstractOIDC.OIDC_DISPLAY,AbstractOIDC.Display.PAGE.getDisplay());
        }else if(device.isMobile()){
            options.put(AbstractOIDC.OIDC_DISPLAY,AbstractOIDC.Display.WAP.getDisplay());
        }
        HttpSession session= request.getSession();
        /**
         * 拼接查询url
         */
        String url= connectAuthEndPoint.connectauthuri(session,scope,clientsecret,clientid,responseType,redirectUri,options);


        log.debug("hold da redirect: {} and state {}",url);

        try {
            response.sendRedirect(url);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
