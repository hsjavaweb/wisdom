package com.wisdom.modules.common.service.impl;

import com.alibaba.fastjson.JSON;
import com.wisdom.framework.core.annotation.ServiceBaseInfo;
import com.wisdom.framework.core.annotation.UpdateCache;
import com.wisdom.framework.core.annotation.UseCache;
import com.wisdom.framework.core.annotation.UseCaches;
import com.wisdom.framework.core.exception.BusinessException;
import com.wisdom.framework.core.service.impl.BaseServiceImpl;
import com.wisdom.framework.core.util.SecureUtil;
import com.wisdom.framework.core.util.id.DefaultIdGenerator;
import com.wisdom.modules.common.domain.Merchant;
import com.wisdom.modules.common.domain.MerchantMini;
import com.wisdom.modules.common.persistence.MerchantMapper;
import com.wisdom.modules.common.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * @author hyberbin on 2016/10/30.
 */
@Service("merchantService")
@ServiceBaseInfo(poType = Merchant.class, mapperType = MerchantMapper.class, uniqueKey = "mercId")
@UseCaches(expireSeconds = 60*60)
public class MerchantServiceImpl extends BaseServiceImpl<Merchant> implements MerchantService {

    @Autowired
    private MerchantMapper merchantMapper;

    @Override
    @UpdateCache(keys = "'*'")
    public int insert(Merchant merchant) throws BusinessException {
        try {
            if (merchant.getMercId() == null) {
                merchant.setMercId(DefaultIdGenerator.defaultIdGenerator.getNextId());
            }
            return super.insert(merchant);
        } catch (Exception e) {
            log.error("写入数据出错！{}", e, JSON.toJSONString(merchant));
            throw new BusinessException(e.getMessage());
        }

    }

    /**
     * 因为有实时余额信息所以不能缓存
     * @param mercId
     * @return
     * @throws BusinessException
     */
    @Override
    public Merchant getMerchantWholeInfo(String mercId) throws BusinessException {
        try {
            return merchantMapper.getMerchantWholeInfo(mercId);
        } catch (DataAccessException e) {
            log.error("获取数据出错！{}", e, mercId);
            throw new BusinessException(e.getMessage());
        }
    }

    @UseCache(key = "#mercId")
    @Override
    public MerchantMini getMerchantWholeInfoFormCache(String mercId) throws BusinessException {
        try {
            return merchantMapper.getMerchantWholeInfo(mercId);
        } catch (DataAccessException e) {
            log.error("获取数据出错！{}", e, mercId);
            throw new BusinessException(e.getMessage());
        }
    }

}
