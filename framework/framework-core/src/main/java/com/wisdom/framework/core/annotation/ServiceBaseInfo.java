package com.wisdom.framework.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hyberbin on 2017/8/19.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ServiceBaseInfo {
    Class poType();

    Class mapperType() default Void.class;

    String uniqueKey();

    String ignoreTests() default "";
}
