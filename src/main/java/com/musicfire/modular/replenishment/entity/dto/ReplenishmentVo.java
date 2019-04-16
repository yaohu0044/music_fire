package com.musicfire.modular.replenishment.entity.dto;

import com.musicfire.modular.replenishment.entity.Replenishment;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReplenishmentVo extends Replenishment {
    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 进价
     */

    private BigDecimal purchasePrice;


    private BigDecimal price;

    private String path;
    private String phone;
    private String merchantName;

    private Long commodityId;
}
