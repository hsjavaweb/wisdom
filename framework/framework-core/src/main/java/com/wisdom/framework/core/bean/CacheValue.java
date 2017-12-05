package com.wisdom.framework.core.bean;

import lombok.Data;


/**
 * @author hyberbin on 2017/8/26.
 */
@Data
public class CacheValue<T> {
    private T data;
    private long expireSeconds=0L;

    public CacheValue() {
    }

    public CacheValue(T data, long expireSeconds) {
        this.data = data;
        this.expireSeconds = expireSeconds;
    }

    public CacheValue(T data) {
        this.data = data;
    }
}
