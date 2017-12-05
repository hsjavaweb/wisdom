package com.wisdom.framework.core.context;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

/**
 * @author hyberbin on 2016/10/30.
 */
public class SpringContextUtil extends PropertyPlaceholderConfigurer implements ApplicationContextAware {

    /**
     * 设置为静态变量
     */
    private static ApplicationContext applicationContext;

    private static Properties systemProperties=new Properties();

    /**
     * Return a merged Properties instance containing both the
     * loaded properties and properties set on this FactoryBean.
     */
    @Override
    protected Properties mergeProperties() throws IOException {
        Properties properties = super.mergeProperties();
        systemProperties.putAll(properties);
        systemProperties.putAll(System.getenv());
        return properties;
    }

    /**
     * 获取ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * 根据bean name获取bean
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    /**
     * 根据bean name获取bean，在spring容器初始化过程中使用
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name, ApplicationContext applicationContext) {
        return (T) applicationContext.getBean(name);
    }

    /**
     * 根据bean class获取bean,如果多个符合则取第一个
     */
    public static <T> T getBean(Class<T> clazz) {
        Map<String, T> beanMaps = applicationContext.getBeansOfType(clazz);
        if (beanMaps != null && !beanMaps.isEmpty()) {
            return beanMaps.values().iterator().next();
        } else {
            return null;
        }
    }

    /**
     * 根据bean class获取bean,如果多个符合则取第一个
     */
    public static <C> Collection<C> getBeans(Class<C> clazz) {
        Map<String, C> beanMaps = applicationContext.getBeansOfType(clazz);
        if (beanMaps != null && !beanMaps.isEmpty()) {
            return beanMaps.values();
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    /**
     * 根据bean class获取bean,如果多个符合则取第一个，在spring容器初始化过程中使用
     */
    public static <T> T getBean(Class<T> clazz, ApplicationContext applicationContext) {
        Map<String, T> beanMaps = applicationContext.getBeansOfType(clazz);
        if (beanMaps != null && !beanMaps.isEmpty()) {
            return beanMaps.values().iterator().next();
        } else {
            return null;
        }
    }


    public static String getSystemProperties(String key){
        return (String) systemProperties.get(key);
    }

    public static Map getSystemProperties(){
        return systemProperties;
    }
}