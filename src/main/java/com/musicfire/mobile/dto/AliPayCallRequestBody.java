package com.musicfire.mobile.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class AliPayCallRequestBody implements Serializable {




    private String subject;

    @JSONField(name = "out_trade_no")
    private String outTradeNo;

    @JSONField(name = "total_amount")
    private BigDecimal totalAmount;

    @JSONField(name = "product_code")
    private String productCode = "QUICK_WAP_WAY";



}
