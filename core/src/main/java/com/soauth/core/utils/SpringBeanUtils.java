package com.soauth.core.utils;

import org.springframework.context.ApplicationContext;

/**
 * Created by zhoujie on 2017/9/25.
 * get beans
 */
public class SpringBeanUtils {
       private  static ApplicationContext applicationContext;

    public static <T> T getBean(Class<T> clazz) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanId) {
        if (applicationContext == null) {
            return null;
        }
        return (T) applicationContext.getBean(beanId);
    }
}
