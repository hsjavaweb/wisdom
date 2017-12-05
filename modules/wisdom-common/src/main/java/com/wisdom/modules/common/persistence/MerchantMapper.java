package com.wisdom.modules.common.persistence;


import com.wisdom.framework.core.data.Mapper;
import com.wisdom.modules.common.domain.Merchant;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

public interface MerchantMapper extends Mapper<Merchant> {

    Merchant getMerchantWholeInfo(String mercId) throws DataAccessException;

    Merchant getMerchantKey(@Param("mercId") String mercId, @Param("mercSafeKey") String mercSafeKey) throws DataAccessException;

    int batchRemove(String... ids);
}
