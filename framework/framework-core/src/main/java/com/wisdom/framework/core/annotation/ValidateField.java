package com.wisdom.framework.core.annotation;

import com.wisdom.framework.core.enums.ValidateType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hyberbin on 2016/11/8.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ValidateField {
    ValidateType type() default ValidateType.NOT_NULL;
    String note() ;
}
