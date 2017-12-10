package com.wisdom.modules.user.persistence;

import com.wisdom.framework.core.data.Mapper;
import com.wisdom.modules.user.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;


public interface UserMapper extends Mapper<User>{

    User findById(int id);
}
