package com.wisdom.framework.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisdom.framework.core.annotation.ServiceBaseInfo;
import com.wisdom.framework.core.annotation.UpdateCache;
import com.wisdom.framework.core.context.SpringContextUtil;
import com.wisdom.framework.core.data.Mapper;
import com.wisdom.framework.core.data.Pager;
import com.wisdom.framework.core.exception.BusinessException;
import com.wisdom.framework.core.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

/**
 * @author hyberbin on 2016/10/30.
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {
    protected Logger log = LoggerFactory.getLogger(getClass());

    protected Mapper<T> mapper;

    public BaseServiceImpl() {
        this.mapper= (Mapper<T>) SpringContextUtil.getBean(this.getClass().getAnnotation(ServiceBaseInfo.class).mapperType());
    }


    @Override
    public List<T> list(Map map) throws BusinessException {
        try {
            return mapper.list(map);
        } catch (DataAccessException e) {
            log.error("查询数据出错！", e);
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Pager<T> listPager(int startPage, int pageLength, Map map) throws BusinessException {
        try {
            PageHelper.startPage(startPage, pageLength);
            List list = mapper.list(map);
            PageInfo pageInfo = new PageInfo(list);
            Pager pager = new Pager(pageInfo.getTotal(),pageInfo.getList());
            return pager;
        } catch (DataAccessException e) {
            log.error("查询数据出错！", e);
            throw new BusinessException(e.getMessage());
        }
    }

    @UpdateCache(keys = "#po.$uniqueKey")
    @Override
    public int insert(T po) throws BusinessException {
        try {
            return mapper.insert(po);
        } catch (DataAccessException e) {
            log.error("写入数据出错！{}", e, JSON.toJSONString(po));
            throw new BusinessException(e.getMessage());
        }
    }


    @UpdateCache(keys = "#po.$uniqueKey")
    @Override
    public int update(T po) throws BusinessException {
        try {
            return mapper.update(po);
        } catch (DataAccessException e) {
            log.error("更新数据出错！{}", e, JSON.toJSONString(po));
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public T getByUniqueKey(String id) throws BusinessException {
        try {
            return mapper.getByUniqueKey(id);
        } catch (DataAccessException e) {
            log.error("获取数据出错！{}", e, id);
            throw new BusinessException(e.getMessage());
        }
    }
    @UpdateCache(keys = "#ids")
    @Override
    public int batchRemove(String... ids) throws BusinessException {
        try {
            return mapper.batchRemove(ids);
        } catch (DataAccessException e) {
            log.error("删除数据出错！{}", e, JSON.toJSONString(ids));
            throw new BusinessException(e.getMessage());
        }
    }

}
