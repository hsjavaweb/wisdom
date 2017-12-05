package com.wisdom.framework.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hyberbin on 2017/8/20.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface UseCache {
    /**
     * 缓存过期时间（秒）
     * @return
     */
    long expireSeconds() default 0;

    /**
     * 缓存key的EL表达式
     * @return
     */
    String key() default "";

    /**
     * 缓存名称
     * @return
     */
    String cacheName() default "";

    /**
     * 如果是List则要指明子类
     * @return
     */
    Class subType() default Void.class;
}
