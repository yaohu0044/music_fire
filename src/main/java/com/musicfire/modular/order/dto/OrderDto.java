package com.musicfire.modular.order.dto;

import com.musicfire.modular.order.entity.Order;
import lombok.Data;

@Data
public class OrderDto extends Order {
    private String merchantName;
    private String machineName;
    private String commodityName;
}
