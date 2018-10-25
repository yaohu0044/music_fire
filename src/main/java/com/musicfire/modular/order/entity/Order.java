package com.musicfire.modular.order.entity;

import java.math.BigDecimal;
import java.util.Date;
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
@TableName("order")
public class Order extends Model<Order> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 机器Id
     */
    @TableField("machine_id")
    private Integer machineId;
    /**
     * 商品Id
     */
    @TableField("commodity_id")
    private Integer commodityId;
    /**
     * 商家Id
     */
    @TableField("merchant_id")
    private Integer merchantId;
    /**
     * 仓位ID
     */
    @TableField("machine_position_id")
    private Integer machinePositionId;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 支付方式
     */
    @TableField("payment_method")
    private Integer paymentMethod;
    /**
     * 订单状态
     */
    private Integer state;
    /**
     * 下单时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 订单号
     */
    private String number;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getMachinePositionId() {
        return machinePositionId;
    }

    public void setMachinePositionId(Integer machinePositionId) {
        this.machinePositionId = machinePositionId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Order{" +
        ", id=" + id +
        ", machineId=" + machineId +
        ", commodityId=" + commodityId +
        ", merchantId=" + merchantId +
        ", machinePositionId=" + machinePositionId +
        ", price=" + price +
        ", paymentMethod=" + paymentMethod +
        ", state=" + state +
        ", createTime=" + createTime +
        ", number=" + number +
        "}";
    }
}
