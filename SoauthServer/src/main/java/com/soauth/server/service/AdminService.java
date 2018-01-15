package com.soauth.server.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhoujie
 * @date 2017/12/12
 */
public interface AdminService {


    /**
     * 返回服务器端管理页面的左侧栏数据
     * @param id
     * @param request
     * @param response
     * @return
     */
    Map<String, List<Object>> adminSidebar(Long id, HttpServletRequest request, HttpServletResponse response);
}
