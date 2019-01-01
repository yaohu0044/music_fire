package com.musicfire.modular.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderReport {

    private Integer orderNum;
    private BigDecimal orderPrice;
    private String orderDate;
    /**
     * 支付方式
     */
    private String paymentMethodStr;
}
