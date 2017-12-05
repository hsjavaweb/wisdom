package com.wisdom.framework.core.service;

import com.wisdom.framework.core.exception.BusinessException;

/**
 * @author hyberbin on 2016/11/4.
 */
public interface UpdateUserLoginService {
    void updateLoginIp(String userName,String ip)throws BusinessException;

    public boolean addIp(String ip,String userName)throws BusinessException;

    public String getUserIpLimit(String userName);
}
