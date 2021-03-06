package com.musicfire.modular.commodity.entity.Dto;

import com.musicfire.modular.commodity.entity.Commodity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CommodityDto extends Commodity {

    private String path;
    private BigDecimal price;

}
