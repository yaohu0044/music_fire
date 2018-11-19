package com.musicfire.mobile.entity;

import lombok.Data;

import java.util.Date;

@Data
public class WechatMpUser {
    /**
     * 主键
     */
    private String id;

    /**
     * 微信OPEN_ID
     */
    private String openId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别
     */
    private String sex;

    /**
     * 语言
     */
    private String language;

    /**
     * 城市
     */
    private String city;

    /**
     * 国家
     */
    private String country;

    /**
     * 头像
     */

    private String headImgUrl;

    /**
     * 性别ID
     */

    private Integer sexId;


    /**
     * 管理员权限 1为管理员 0为普通用户
     */
    private Integer isAdmin;


    /**
     * 是否可用
     */
    private Integer deleted;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 创建时间
     */

    private Date createTime;

    /**
     * 修改时间
     */

    private Date updateTime;

}