package com.wisdom.modules.common.persistence;


import com.wisdom.framework.core.data.Mapper;
import com.wisdom.modules.common.domain.MerchantsUsers;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

public interface MerchantsUsersMapper extends Mapper<MerchantsUsers> {

    int getMercUserCountByRoleId(String roleId)throws DataAccessException;

    String getAdminMail(@Param("mercId") String mercId)throws DataAccessException;

    int batchRemove(@Param("mercId") String mercId,@Param("array") String... ids);
}

