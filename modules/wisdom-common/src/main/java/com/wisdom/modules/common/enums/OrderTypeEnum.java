/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.wisdom.modules.common.enums;

/**
 *
 * order_type
 * 订单类型 1收款 2充值 3提现 4代付.
 * @author hyberbin
 * @version $Id: OrderTypeEnum.java, v 0.1 2017年12月03日 12:15 hyberbin Exp $
 */
public enum OrderTypeEnum {
                       pay(1), recharge(2), remit(3), @Deprecated
                       payout(4);

    private int value;

    OrderTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}