package com.wisdom.modules.common.service;

import com.wisdom.framework.core.exception.BusinessException;
import com.wisdom.framework.core.service.BaseService;
import com.wisdom.modules.common.domain.MerchantsUsers;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author hyberbin on 2016/10/30.
 */
public interface MerchantsUsersService extends BaseService<MerchantsUsers> {

    int getMercUserCountByRoleId(String roleId) throws BusinessException;

    String getAdminMail(String mercId) throws BusinessException;

    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;

    int batchRemoveByMercId(String mercId,String... ids) throws BusinessException;
}

