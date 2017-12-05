package com.wisdom.framework.core.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

/**
 * 国际化异常基类
 */
public class BaseException extends Exception {
    private static Logger logger = LoggerFactory.getLogger(BaseException.class);

    public BaseException() {
        super();
    }

    public BaseException(String message, Object... arguments) {
        super(format(message, arguments));
    }

    private static String format(String message, Object... arguments) {
        int index = 0;
        while (message.contains("{}")) {
            message = message.replaceFirst("\\{\\}", "{" + index++ + "}");
        }
        return index > 0 ? MessageFormat.format(message, arguments) : message;
    }
}