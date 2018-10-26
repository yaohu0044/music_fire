package com.musicfire.modular.commodity.entity.Dto;

import com.baomidou.mybatisplus.annotations.TableField;

import java.math.BigDecimal;
import java.util.Date;

public class CommodityDto {
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
    private BigDecimal purchasePrice;
    /**
     * 商家Id
     */
    private Integer merchantId;
    /**
     * false:未删除，true，删除
     */
    private Boolean flag;
    private Date createTime;
    private Date updateTime;
    /**
     * 图片路径
     */
    private String path;
    /**
     * 库存量
     */
    private Integer num;
    /**
     * 价格
     */
    private BigDecimal price;

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

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
