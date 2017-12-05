/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.wisdom.modules.common.enums;

/**
 * @author hyberbin
 * @version $Id: CardTypeEnum.java, v 0.1 2017年12月03日 13:45 hyberbin Exp $
 */
public enum CardTypeEnum {
                          WithdrawCard(0), TransferCard(1);

    private int value;

    CardTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}