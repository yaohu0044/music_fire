package com.musicfire.modular.system.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserVo {
    private Integer id;
    /**
     * 用户名
     */
    private String name;
    /**
     * 性别
     */
    private Boolean gender;
    /**
     * 密码
     */
    private String password;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 出生日期
     */
    private Date dateBirth;
    /**
     * 手机
     */
    private String phone;
    /**
     * QQ号
     */
    private String qq;
    /**
     * 头像
     */
    private String headPortrait;
    /**
     * 电话
     */
    private String tel;
    /**
     * 邮箱
     */
    private String email;
}
