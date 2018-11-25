package com.musicfire.modular.commodity.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
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
public class Commodity extends Model<Commodity> {

    private static final long serialVersionUID = 1L;

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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
