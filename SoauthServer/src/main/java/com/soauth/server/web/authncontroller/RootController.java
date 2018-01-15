package com.soauth.server.web.authncontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping()
    public String home(){
        return "root/home";
    }

    
}
