package com.wisdom.modules.common.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author hyberbin on 2016/10/30.
 */
@Getter
@Setter
public class Merchant extends MerchantMini implements Serializable {

    /**
     * 余额
     */
    private Float amount;
    /**
     * 代付余额
     */
    private Float proxyAmount;
    /**
     * 充值冻结余额
     */
    private Float frozenCashAmount;
    /**
     * 代付冻结余额
     */
    private Float frozenProxyAmount;
    /**
     * 累计交易金额
     */
    private Float txAmount;
    /**
     * 累计提现金额
     */
    private Float remitAmount;

    /**
     * 银行通知次数
     */
    private Long bnkNotifyCnt;
    /**
     * 商户通知次数
     */
    private Long mercNotifyCnt;
    /**
     * 版本号
     */
    private Integer versionNo;
}
