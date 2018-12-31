package com.musicfire.modular.commodity.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
@TableName("commodity")
@Data
public class Commodity {

    private Integer id;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 描述
     */
    private String describe;
    /**
     * 简介
     */
    private String introduce;

    @TableField("introduce_content")
    private String introduceContent;
    /**
     * 进价
     */
    @TableField("purchase_price")
    private BigDecimal purchasePrice;
    private BigDecimal price;
    /**
     * false:未删除，true，删除
     */
    private Boolean flag;
    @TableField("create_time")
    private Date createTime;
    @TableField("create_id")
    private Integer createId;
    @TableField("update_time")
    private Date updateTime;
    @TableField("update_id")
    private Integer updateId;


}
