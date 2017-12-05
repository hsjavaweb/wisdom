package com.wisdom.modules.common.service;

import com.wisdom.framework.core.exception.BusinessException;
import com.wisdom.framework.core.service.BaseService;
import com.wisdom.modules.common.domain.Merchant;
import com.wisdom.modules.common.domain.MerchantMini;

/**
 * @author hyberbin on 2016/10/30.
 */
public interface MerchantService extends BaseService<Merchant> {

    Merchant getMerchantWholeInfo(String mercId) throws BusinessException;

    MerchantMini getMerchantWholeInfoFormCache(String mercId) throws BusinessException;
}
