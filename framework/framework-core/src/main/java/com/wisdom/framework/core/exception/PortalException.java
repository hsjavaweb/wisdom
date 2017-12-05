package com.wisdom.framework.core.exception;

/**
 * @author hyberbin on 15/7/3.
 */
public class PortalException extends BaseException {
    /**
     * 构造方法
     *
     * @param message 消息语言 key
     * @param arguments  参数
     */
    public PortalException(String message, Object... arguments) {
        super(message, arguments);
    }
}
