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
public class MerchantsUsers implements Serializable{

    private static final long serialVersionUID = 105261477713491847L;

    /**
     * id字段.
     * .
     */

    private Long id;
    /**
     * merc_id字段.
     * 商户id.
     */
    @ValidateField(note = "商户ID")
    private String mercId;
    /**
     * merc_id字段.
     * 商户.
     */
    private String mercName;
    /**
     * admin_type字段.
     * 0.游客，1，普通管理员，2商户管理员，3系统管理员
     * 管理员类型.
     */
    @ValidateField(note = "管理员类型")
    private Integer adminType;

    @ValidateField(note = "管理员角色")
    private Integer roleId;
    /**
     * 语言
     */
    private String lang;
    /**
     * 邮箱
     */
    private String email;
    /**
     * user_name字段.
     * 用户名.
     */
    @ValidateField(note = "用户名")
    private String userName;
    /**
     * pass_word字段.
     * 密码.
     */
    @ValidateField(note = "密码")
    private String passWord;

    private String oldPassWord;
    /**
     * last_login_time字段.
     * 最后登录时间.
     */

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date lastLoginTime;
    /**
     * last_login_ip字段.
     * 最后登录ip.
     */

    private String lastLoginIp;
    /**
     * ip_limit字段.
     * ip限制.
     */
    @ValidateField(note = "ip限制")
    private String ipLimit;
    /**
     * del_flag字段.
     * 是否删除.
     */

    private Integer delFlag;
    /**
     * create_time字段.
     * 创建时间.
     */

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * last_update_time字段.
     * 最后更新时间.
     */

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdateTime;
    /**
     * version_no字段.
     * 版本号.
     */

    private Integer versionNo;
}

