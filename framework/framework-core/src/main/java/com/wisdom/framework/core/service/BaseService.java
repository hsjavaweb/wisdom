package com.wisdom.framework.core.service;

import com.wisdom.framework.core.exception.BusinessException;
import com.wisdom.framework.core.data.Pager;

import java.util.List;
import java.util.Map;

/**
 * @author hyberbin on 2016/10/30.
 */
public interface BaseService<T> {
    List<T> list(Map map)throws BusinessException;

    Pager<T> listPager(int startPage, int pageLength, Map map) throws BusinessException;

    int insert(T po)throws BusinessException;

    int update(T po)throws BusinessException;

    T getByUniqueKey(String id)throws BusinessException;

    int batchRemove(String... ids) throws BusinessException;
}
