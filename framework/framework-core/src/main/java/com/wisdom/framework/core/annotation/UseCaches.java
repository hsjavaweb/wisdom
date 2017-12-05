package com.wisdom.framework.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hyberbin on 2017/8/20.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface UseCaches {
    /**
     * 缓存过期时间（秒）
     * @return
     */
    long expireSeconds() default 0;

    String cacheName() default "";

}
