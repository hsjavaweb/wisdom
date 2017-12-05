/**
 * hyberbin.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.wisdom.framework.core.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author hyberbin
 * @version $Id: Cacheable.java, v 0.1 2017年11月13日 17:56 hyberbin Exp $
 */
public interface Cacheable {
    /**
     * 获取缓存摘要
     * @return
     */
    @JSONField(serialize = false,deserialize = false)
    @JsonIgnore
    String getDigest();
}