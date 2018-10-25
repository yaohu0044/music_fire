package com.musicfire.modular.commodity.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
@TableName("commodity_stock")
public class CommodityStock extends Model<CommodityStock> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 商品Id
     */
    @TableField("commodity_id")
    private Integer commodityId;
    /**
     * 库存量
     */
    private Integer num;
    /**
     * 价格
     */
    private BigDecimal price;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CommodityStock{" +
        ", id=" + id +
        ", commodityId=" + commodityId +
        ", num=" + num +
        ", price=" + price +
        "}";
    }
}
