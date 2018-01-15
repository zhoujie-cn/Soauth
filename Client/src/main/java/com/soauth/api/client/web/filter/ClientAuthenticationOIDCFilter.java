package com.soauth.api.client.web.filter;

import com.soauth.core.vo.oauth2.AbstractOIDC;
import org.apache.commons.lang.StringUtils;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.naming.AuthenticationException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author  zhoujie
 * @date 2017/12/3
 */
public class ClientAuthenticationOIDCFilter  extends AccessControlFilter{

    /**
     *  ¶ÔclientÇëÇó×öÀ¹½Ø
     * @param request
     * @param response
     * @param mappedValue
     * @return
     * @throws Exception
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        //error request
        if(StringUtils.isNotEmpty(request.getParameter(OAuthError.OAUTH_ERROR)) || StringUtils.isNotEmpty(request.getParameter(OAuthError.OAUTH_ERROR_DESCRIPTION))){


        }else if(StringUtils.isNotEmpty(request.getParameter(OAuth.OAUTH_CODE))){
            // success request
            handlerCodeToken(request,response);
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return false;
    }


    private void handlerCodeToken(ServletRequest request, ServletResponse response) {

        RequestDispatcher dispatcher = request.getRequestDispatcher(request.getParameter(OAuth.OAUTH_REDIRECT_URI));

        try {
            dispatcher.forward(request, response);

        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }


}
