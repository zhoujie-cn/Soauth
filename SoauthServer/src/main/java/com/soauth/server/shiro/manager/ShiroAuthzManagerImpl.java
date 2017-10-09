package com.soauth.server.shiro.manager;

import com.soauth.server.utils.LoadINIutis;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhoujie on 2017/9/27.
 * <bean id="shiroManager" class="com.soauth.server.shiro.manager.ShiroAuthzManagerImpl"> </bean>
 *
 * shiro 过滤器管理
 */
public class ShiroAuthzManagerImpl implements ShiroAuthzManager {

     private static  final Logger log = LoggerFactory.getLogger(ShiroAuthzManagerImpl.class);

     @Resource
     @Autowired
     private ShiroFilterFactoryBean  shiroFilterFactoryBean;

    private  String auzFilename="shiro_authz.ini";
    @Override
    public String loadauthzFilesinits() {
       log.info("load authz..");
       StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(getauthzini());
        return stringBuffer.toString();
    }


    private String getauthzini(){
        ClassPathResource resource = new ClassPathResource(auzFilename);
        LoadINIutis inIutis=null;
        try {
        inIutis = new LoadINIutis(resource.getFile());

        } catch (IOException e) {
            if(e instanceof FileNotFoundException) {
                log.error("classpath{} file not Found" , e.getMessage());
            }
            log.error("IO Error{}",e.getMessage());
        }

        String section = "base_auth";
        Set<String> keys = inIutis.get(section).keySet();
        StringBuffer sb = new StringBuffer();
        for (String key : keys) {
            String value =inIutis.get(section, key);
            sb.append(key).append(" = ").append(value).append("\r\n");
        }

        return sb.toString();

    }

    @Override
    public void reCreateFilterChains() {
        AbstractShiroFilter shiroFilter= null;
        try {
            shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
        } catch (Exception e) {
            log.error("getShiroFilter from shiroFilterFactoryBean error!", e);
            throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
        }

        PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
                .getFilterChainResolver();
        DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver
                .getFilterChainManager();

    }

    /**
     *  同步清除并且构建拦截器ini文件内容
     * @param manager
     */
    private  void clearandbuildFilters( DefaultFilterChainManager manager ){
        manager.getFilterChains().clear();
        shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
        synchronized(this){
            shiroFilterFactoryBean.setFilterChainDefinitions(loadauthzFilesinits()); // build shiro filter

            // build manager
            Map<String, String> chains = shiroFilterFactoryBean
                    .getFilterChainDefinitionMap();

            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim().replace(" ", "");
                manager.createChain(url, chainDefinition);
            }
        }

    }

}
