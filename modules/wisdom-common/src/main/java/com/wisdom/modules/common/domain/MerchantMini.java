package com.wisdom.modules.common.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wisdom.framework.core.annotation.ValidateField;
import com.wisdom.framework.core.util.json.serializer.JsonDateTimeDeserializer;
import com.wisdom.framework.core.util.json.serializer.JsonDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author hyberbin on 2016/10/30.
 */
@Getter
@Setter
public class MerchantMini implements Serializable {

    private Long id;
    /**
     * 商户ID
     */
    @ValidateField(note = "商户ID")
    private String mercId;
    /**
     * 商户ID
     */
    @ValidateField(note = "商户名")
    private String mercName;
    /**
     * 商户密钥
     */
    @ValidateField(note = "商户密钥")
    private String mercKey;
    /**
     * 商户安全码
     */
    @ValidateField(note = "商户安全码")
    private String mercSafeKey;
    /**
     * 费率
     */
    @ValidateField(note = "支付费率")
    private Float payRate;
    @ValidateField(note = "支付费率2")
    private Float payRate2;
    @ValidateField(note = "退款手续费")
    private Float refundRate;
    @ValidateField(note = "拒付手续费")
    private Float chargeBackRate;
    private Integer isRefundAllow;
    /**
     * 费率
     */
    @ValidateField(note = "提现费率")
    private Float cashRate;
    /**
     * 费率
     */
    @ValidateField(note = "代付费率")
    private Float remitRate;

    /**
     * 法人代表
     */
    @ValidateField(note = "法人代表")
    private String legalName;
    /**
     * 联系方式
     */
    private String phone;
    /**
     * 手机
     */
    @ValidateField(note = "手机")
    private String mobile;
    /**
     * 商城地址
     */
    @ValidateField(note = "商城地址")
    private String shopUrl;
    /**
     * 冻结余额
     */
    private Integer delFlag;
    /**
     * IP限制
     */
    private String proxyServerIp;
    /**
     * IP限制
     */
    private String ipLimit;
    /**
     * 域名绑定
     */
    private String host;
    /**
     * 创建时间
     */
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 最后更新时间
     */
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateTime;
}
