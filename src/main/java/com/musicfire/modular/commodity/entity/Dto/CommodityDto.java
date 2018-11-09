package com.musicfire.modular.commodity.entity.Dto;

import com.musicfire.common.validated.Insert;
import com.musicfire.common.validated.Update;
import com.musicfire.modular.commodity.entity.Commodity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class CommodityDto extends Commodity {

    private String merchantName;
    private Integer merchantId;
}
