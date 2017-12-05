package com.wisdom.framework.core.data;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

/**
 * @author hyberbin on 2017/8/19.
 */
public interface Mapper<T> {

    List<T> list(Map map)throws DataAccessException;

    int insert(T subApp)throws DataAccessException;

    int update(T subApp)throws DataAccessException;

    T getByUniqueKey(@Param("id") String id)throws DataAccessException;

    int batchRemove(String... ids) throws DataAccessException;
}
