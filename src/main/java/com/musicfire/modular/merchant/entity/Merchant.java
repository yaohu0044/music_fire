package com.musicfire.modular.merchant.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.musicfire.common.validated.Insert;
import com.musicfire.common.validated.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
@TableName("merchant")
@Data
public class Merchant {

    private static final long serialVersionUID = 1L;

    private Integer id;
    @TableField("user_id")
    private Integer userId;
    /**
     * 商家title
     */
    private String title;
    /**
     * 商家名称
     */
    private String name;
    /**
     * 商家类型
     */
    private Integer type;

    private String logo;

    @TableField("lon_and_lat")
    private String lonAndLat;

    private String describe;

    @TableField("create_time")
    private Date createTime;

    @TableField("create_id")
    private Integer createId;

    @TableField("update_time")
    private Date updateTime;

    @TableField("update_id")
    private Integer updateId;
    /**
     * 商家登录名
     */
    @TableField("login_name")
    private String loginName;
    /**
     * 商家密码
     */
    private String password;

    private String address;

    private Boolean flag;

    private String phone;

}
