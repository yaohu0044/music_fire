package com.musicfire.modular.commodity.dao;

import com.musicfire.common.utiles.ExcelVOAttribute;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExcelCommodity {

    @ExcelVOAttribute(name = "图片地址", column = "A")
    private String path;

    @ExcelVOAttribute(name = "价格", column = "B")
    private BigDecimal price;

    /**
     * 商品名称
     */
    @ExcelVOAttribute(name = "商品名称", column = "C")
    private String name;
    /**
     * 描述
     */
    @ExcelVOAttribute(name = "描述", column = "D")
    private String describe;
    /**
     * 简介
     */
    @ExcelVOAttribute(name = "简介", column = "E")
    private String introduce;

    @ExcelVOAttribute(name = "详情简介", column = "G")
    private String introduceContent;
    /**
     * 进价
     */
    @ExcelVOAttribute(name = "进价", column = "H")
    private BigDecimal purchasePrice;

}
