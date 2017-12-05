/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.wisdom.modules.common.enums;

import com.wisdom.framework.core.exception.ValidateException;

/**
 * @author hyberbin
 * @version $Id: TxTypeEnum.java, v 0.1 2017年12月03日 11:31 hyberbin Exp $
 */
public enum TxTypeEnum {
                        /**
                         * 快捷
                         */
                        QuickPay(1),
                        /**
                         * 网银
                         */
                        OnlineBank(2),
                        /**
                         * 扫码
                         */
                        ScanQr(3),
                        /**
                         * App
                         */
                        AppPay(4),
                        /**
                         * 三方代付
                         */
                        Payout(5),
                        /**
                         * 手机银行
                         */
                        WebBank(6),
                        /**
                         * 转账
                         */
                        Transfer(7);

    private int value;

    TxTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "" + value ;
    }

    public static TxTypeEnum getEnum(int value) {
        switch (value) {
            case 1:
                return QuickPay;
            case 2:
                return OnlineBank;
            case 3:
                return ScanQr;
            case 4:
                return AppPay;
            case 5:
                return Payout;
            case 6:
                return WebBank;
            case 7:
                return Transfer;
            default:
                throw new ValidateException("txType enum error");

        }
    }
}