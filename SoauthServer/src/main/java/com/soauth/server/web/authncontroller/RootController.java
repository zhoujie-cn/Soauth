package com.soauth.server.web.authncontroller;

import com.soauth.core.shiro.utils.ShiroUtils;
import com.soauth.server.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhoujie
 * @date 2017/12/11
 *
 *
 * op管理接口
 */


@Controller
@RequestMapping(value ={"admin"} )
public class RootController {

    @Autowired
    AdminService adminService;

    @RequestMapping()
    public String home(){
        return "root/home";
    }

    @RequestMapping("sidebar")
    @ResponseBody
    public Map<String,List<Object>> retrieveSidebar( HttpServletRequest servletRequest, HttpServletResponse response){

       return adminService.adminSidebar(1l,servletRequest,response );

    }
    
}
