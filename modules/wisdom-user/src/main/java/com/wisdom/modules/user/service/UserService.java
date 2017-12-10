package com.wisdom.modules.user.service;

import com.wisdom.framework.core.data.Pager;
import com.wisdom.framework.core.exception.BusinessException;
import com.wisdom.framework.core.service.BaseService;
import com.wisdom.modules.user.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService extends BaseService<User>{

    User findById(int id) ;
}
