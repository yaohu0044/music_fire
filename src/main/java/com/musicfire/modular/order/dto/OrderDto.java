package com.musicfire.modular.order.dto;

import com.musicfire.modular.order.entity.Order;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDto extends Order {
    private String merchantName;
    private String machineName;
    private String commodityName;
    private String positionNum;
    private String commodityDes;
    private Integer num;
    private String roomName;
    private Integer type;
    private BigDecimal purchasePrice;


}
