package com.wisdom.framework.core.util;

import com.wisdom.framework.core.annotation.ValidateField;
import com.wisdom.framework.core.enums.ValidateType;
import com.wisdom.framework.core.exception.ValidateException;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hyberbin on 2016/11/12.
 */
public class ValidateUtils {
    private static final Map<String, List<ValidateParmBean>> VALIDATE_PARM_BEAN_MAP = new ConcurrentHashMap<String, List<ValidateParmBean>>();

    public static String validate(Object object, String... exclude) {
        return validate(object,true,exclude);
    }

    public static String validate(Object object,boolean superValidate, String... exclude) {
        StringBuilder note = new StringBuilder();
        List<String> strings = Arrays.asList(exclude);
        List<ValidateParmBean> validateParmBeans = getValidateParmBeans(object.getClass());
        for (ValidateParmBean validateParmBean : validateParmBeans) {
            Field field = validateParmBean.getField();
            if(!superValidate&&validateParmBean.isSuperField()) continue;
            if (!strings.contains(field.getName())) {
                Object value = ReflectionUtils.getField(field, object);
                if (validateParmBean.isNotNull && value == null) {
                    note.append(field.getName()).append(" Can not be null,");
                } else if (value != null && validateParmBean.isInteger && !Number.class.isAssignableFrom(object.getClass()) && !value.toString().matches("^[+-]?\\d+$")) {
                    note.append(field.getName()).append(" not integer,");
                } else if (value != null && validateParmBean.isFloat && !Number.class.isAssignableFrom(object.getClass()) && !value.toString().matches("^([+-]?\\d+)(\\.\\d+)?$")) {
                    note.append(field.getName()).append(" not float ,");
                } else if (value != null && validateParmBean.isDate && !Date.class.isAssignableFrom(object.getClass()) && !value.toString().matches("[0-9]{4}-[0-9]{2}-[0-9]{2}$")) {
                    note.append(field.getName()).append(" not date,");
                } else if (value != null && validateParmBean.isDateTime && !Date.class.isAssignableFrom(object.getClass()) && !value.toString().matches("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$")) {
                    note.append(field.getName()).append(" not datetime,");
                } else if (value != null && validateParmBean.isOrderNo && !value.toString().matches("[A-Za-z0-9-]{1,50}$")) {
                    note.append(field.getName()).append(" format error,");
                }
            }
        }
        return note.toString();
    }


    /**
     * 解析一个类中的ICS参数和字段并缓存起来。
     *
     * @param clazz
     */
    public static final List<ValidateParmBean> getValidateParmBeans(Class clazz) {
        List<ValidateParmBean> validateParmBeanList = VALIDATE_PARM_BEAN_MAP.get(clazz.getName());
        if (validateParmBeanList == null) {
            validateParmBeanList = new ArrayList<ValidateParmBean>();
            for (Class<?> superClass = clazz; superClass != null && superClass != Serializable.class; superClass = superClass.getSuperclass()) {
                Field[] declaredFields = superClass.getDeclaredFields();
                for (Field field : declaredFields) {
                    field.setAccessible(true);
                    ValidateParmBean validateParmBean = new ValidateParmBean();
                    validateParmBean.setField(field);
                    if (field.isAnnotationPresent(ValidateField.class)) {
                        ValidateField annotation = field.getAnnotation(ValidateField.class);
                        validateParmBean.setDate(annotation.type() == ValidateType.DATE);
                        validateParmBean.setDateTime(annotation.type() == ValidateType.DATE_TIME);
                        validateParmBean.setNotNull(annotation.type() != ValidateType.OPTIONAL);
                        validateParmBean.setInteger(annotation.type() == ValidateType.INTEGER);
                        validateParmBean.setFloat(annotation.type() == ValidateType.FLOAT);
                        validateParmBean.setOptional(annotation.type() == ValidateType.OPTIONAL);
                        validateParmBean.setOrderNo(annotation.type() == ValidateType.ORDER_NO);
                        validateParmBean.setNote(annotation.note());
                        validateParmBean.setSuperField(superClass!=clazz);
                        validateParmBeanList.add(validateParmBean);
                    }
                }
            }
            Collections.sort(validateParmBeanList);
            VALIDATE_PARM_BEAN_MAP.put(clazz.getName(),validateParmBeanList);
        }
        return validateParmBeanList;
    }


    public static void validate(Object o, String msg) {
        if (o == null||o instanceof Boolean&&!(Boolean)o) {
            throw new ValidateException(msg);
        }
    }

    public static String getValidateSignXml(Object o){
        return getValidateSignXml(o,true);
    }

    public static String getValidateSignXml(Object o,boolean superValidate){
        List<ValidateParmBean> validateParmBeans = getValidateParmBeans(o.getClass());
        StringBuilder verifyData=new StringBuilder("<xml>");
        for(ValidateParmBean parmBean:validateParmBeans){
            if(!superValidate&& parmBean.isSuperField()) continue;
            Object field = ReflectionUtils.getField(parmBean.getField(), o);
            if(parmBean.isNotNull()||parmBean.isFloat()||parmBean.isDate()||parmBean.isDateTime()||parmBean.isInteger()){
                verifyData.append("<").append(parmBean.getField().getName()).append(">")
                        .append(StringEscapeUtils.escapeXml(field +""))
                        .append("</").append(parmBean.getField().getName()).append(">");
            }else if(parmBean.isOptional()&&field!=null){
                verifyData.append("<").append(parmBean.getField().getName()).append(">")
                        .append(StringEscapeUtils.escapeXml(field +""))
                        .append("</").append(parmBean.getField().getName()).append(">");
            }
        }
        verifyData.append("</xml>\n");
        return verifyData.toString();
    }

    @Setter
    @Getter
    private static class ValidateParmBean implements Comparable<ValidateParmBean>{
        private Field field;
        private boolean isNotNull;
        private boolean isInteger;
        private boolean isFloat;
        private boolean isDate;
        private boolean isDateTime;
        private boolean isSuperField;
        private boolean isOptional;
        private boolean isOrderNo;
        private String note;

        @Override
        public int compareTo(ValidateParmBean o) {
            return field.getName().compareTo(o.field.getName());
        }
    }
}
