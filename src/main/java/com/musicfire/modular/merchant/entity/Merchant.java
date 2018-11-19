package com.musicfire.modular.merchant.entity;

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
     * 商家类型
     */
    private Integer type;

    private String logo;

    @TableField("lon_and_lat")
    private String lonAndLat;

    private String describe;
    private String address;

    @TableField("create_time")
    private Date createTime;

    @TableField("create_id")
    private Integer createId;

    @TableField("update_time")
    private Date updateTime;

    @TableField("update_id")
    private Integer updateId;

    private Boolean flag;

}
