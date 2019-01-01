package com.musicfire.modular.order.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

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
@TableName("`order`")
@Data
public class Order implements Serializable {

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
    @TableField("machine_position_Num")
    private Integer machinePositionNum;
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

    @TableField("account_account")
    private String accountAccount;

    @TableField("unified_num")
    private String unifiedNum;

    @TableField("trade_no")
    private String tradeNo;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String headImg;

}
