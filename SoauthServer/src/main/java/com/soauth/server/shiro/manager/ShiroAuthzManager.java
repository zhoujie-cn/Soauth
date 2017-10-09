package com.soauth.server.shiro.manager;

/**
 * Created by zhoujie on 2017/9/27.
 */
public interface ShiroAuthzManager {

    /**
     * 加载过滤配置信息
     * @return
     */
    public String loadauthzFilesinits();

    /**
     * 重新构建权限过滤器
     * 一般在修改了用户角色、用户等信息时，需要再次调用该方法
     */
    public void reCreateFilterChains();

}
