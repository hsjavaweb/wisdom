package com.wisdom.framework.core.util;


import com.wisdom.framework.core.exception.ValidateException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * @author hyberbin on 2016/11/6.
 */
public class NumberUtils {
    /**
     * 两个数相乘，保留两位小数，后面直接进1
     *
     * @param a
     * @param b
     * @return
     */
    public static Float multiply(Float a, Float b) {
        return multiply(a.toString(),b.toString());
    }

    public static Float multiply(String a, String b) {
        BigDecimal multiply = new BigDecimal(a).multiply(new BigDecimal(b));
        return multiply.setScale(2, RoundingMode.UP).floatValue();
    }

    public static Float add(Float a, Float b) {
        return add(a.toString(),b.toString());
    }

    public static Float add(String a, String b) {
        BigDecimal multiply = new BigDecimal(a).add(new BigDecimal(b));
        return multiply.setScale(2, RoundingMode.UP).floatValue();
    }

    public static Float subtract(Float a, Float b) {
        return subtract(a.toString(),b.toString());
    }

    public static Float subtract(String a, String b) {
        BigDecimal multiply = new BigDecimal(a).subtract(new BigDecimal(b));
        return multiply.setScale(2, RoundingMode.UP).floatValue();
    }

    public static String getFloatRandom(String s){
        if(!s.contains(".")||s.endsWith(".0")||s.endsWith(".00")){
            int random=new Random().nextInt(5)+1;
            return new BigDecimal(s).subtract(new BigDecimal("0.0"+random)).toString();
        }
        return s;
    }

    /**
     * 用轮船赌算法返回本次随机选中的值的下标.
     *
     * @param nums
     * @return
     */
    public static int getRandom(int[] nums) {
        if (nums.length > 1) {
            int sum = 0;
            //求和
            for (int n = 0; n < nums.length; n++) {
                sum += nums[n];
            }
            //使用轮船赌算法鸡产生随机数，返回对应数的下标
            int rand = new Random().nextInt(sum);
            sum = 0;
            for (int n = 0; n < nums.length; n++) {
                sum += nums[n];
                if (rand <= sum) {
                    return n;
                }
            }
        }
        return 0;
    }

    public static Float parseFloat(String value){
        return Float.valueOf(value.replace(",",""));
    }

    /**
     * yuan to fen
     */
    public static int convertYuan2Fen(Object filedValue) {
        try {
            BigDecimal localBigDecimal = new BigDecimal(parseFloat(filedValue.toString()));
            return localBigDecimal.movePointRight(2).intValue();
        } catch (Throwable e) {
            throw new ValidateException(filedValue+" ,format error!");
        }
    }
}
