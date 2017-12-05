package com.wisdom.framework.core.exception;

/**
 * 业务异常
 */
public class BusinessException extends BaseException {
    /**
     * 构造方法
     *
     * @param message   消息语言 key
     * @param arguments 参数
     */
    public BusinessException(String message, Object... arguments) {
        super(message, arguments);
    }
}
