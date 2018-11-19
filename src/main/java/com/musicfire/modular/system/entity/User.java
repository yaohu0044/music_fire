package com.musicfire.modular.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
@TableName("sys_user")
@Data
public class User{

    private static final long serialVersionUID = 1L;

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
    @TableField("login_name")
    private String loginName;
    /**
     * 出生日期
     */
    @TableField("date_birth")
    private String dateBirth;
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
    @TableField("head_portrait")
    private String headPortrait;
    /**
     * 电话
     */
    private String tel;
    /**
     * 邮箱
     */
    private String email;
    /**
     * false:未删除，true，删除
     */
    private Boolean flag;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 创建者
     */
    @TableField("create_id")
    private Integer createId;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 修改者
     */
    @TableField("update_id")
    private Integer updateId;


}
