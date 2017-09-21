package com.exhibit.rpc.util;

import java.lang.reflect.Field;
import java.util.Map;

import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
/**
 * @author: ankang
 */
@Component
public class SpringApplicationContextUtil implements ApplicationContextAware{
	
	// Spring应用上下文环境
    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringApplicationContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * @throws BeansException
     */
    public static Object getBean(Class<?> cls) throws BeansException {
        return applicationContext.getBean(cls);
    }

    /**
     * @throws BeansException
     */
    public static <T> T getBeansOfType(Class<T> clazz) {
        return (T) applicationContext.getBeansOfType(clazz).values().iterator().next();
    }

    public static <T> Map<String, T> getBeanNamesOfType(Class<T> clazz) {
    	return applicationContext.getBeansOfType(clazz);
    }
    
    /**
     * @throws BeansException
     */
    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }

    /**
     * 获取使用 Proxy.newProxyInstance 生成的代理对象
     *
     * @throws Exception
     */
    @SuppressWarnings({"unchecked"})
    public static <T> T getProxyObject(Object proxy, Class<T> thisClass) throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        return (T) h.get(proxy);
    }
    
    /**
     * 获取经过CGLIB包装过的原始target bean
     *
     *
     * @throws Exception
     */
    @SuppressWarnings({"unchecked"})
    public static <T> T getTargetObject(Object proxy, Class<T> targetClass) throws Exception {

        return (T) ((Advised) proxy).getTargetSource().getTarget();
    }
}
