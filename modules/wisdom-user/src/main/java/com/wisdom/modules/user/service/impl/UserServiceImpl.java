package com.wisdom.modules.user.service.impl;

import com.wisdom.framework.core.annotation.ServiceBaseInfo;
import com.wisdom.framework.core.exception.BusinessException;
import com.wisdom.framework.core.service.impl.BaseServiceImpl;
import com.wisdom.modules.user.domain.User;
import com.wisdom.modules.user.persistence.UserMapper;
import com.wisdom.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
@ServiceBaseInfo(poType = User.class, mapperType = User.class,uniqueKey = "id")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findById(int id)  {

        User user = userMapper.findById(id);

        return user;
    }
}
