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
public @interface UpdateCache {
    /**
     * 缓存key的EL表达式,*代表清空
     * @return
     */
    String[] keys() default {};

    /**
     * 缓存名称
     * @return
     */
    String[] cacheNames() default {};
}
