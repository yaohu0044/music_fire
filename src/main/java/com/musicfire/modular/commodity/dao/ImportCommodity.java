package com.musicfire.modular.commodity.dao;

import com.musicfire.common.utiles.ExcelVOAttribute;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ImportCommodity {

    /**
     * 商品名称
     */
    @ExcelVOAttribute(name = "商品名称", column = "A")
    private String name;

    /**
     * 描述
     */
    @ExcelVOAttribute(name = "描述", column = "B")
    private String describe;
    /**
     * 进价
     */
    @ExcelVOAttribute(name = "进价", column = "C")
    private String purchasePrice;

    /**
     * 图片路径
     */
    @ExcelVOAttribute(name = "图片路径", column = "D")
    private String path;
    /**
     * 库存量
     */
    @ExcelVOAttribute(name = "库存量", column = "E")
    private Integer num;
    /**
     * 价格
     */
    @ExcelVOAttribute(name = "价格", column = "F")
    private String price;


}
