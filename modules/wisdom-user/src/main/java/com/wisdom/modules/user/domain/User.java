package com.wisdom.modules.user.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class User {
    private int id; // 用户编号
    private String username; // 用户姓名
    private String password; // 用户密码
    private String gender; // 用户性别
    private String email; // 用户邮箱
    private String telephone; // 用户联系电话
    private String introduce; // 用户介绍
    private String activeCode; // 激活码
    private String role; // 用户角色
    private int state; // 用户状态
    private Date registTime;// 注册时间
}
