package com.musicfire.modular.merchant.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.musicfire.common.utiles.ExcelVOAttribute;
import lombok.Data;

@Data
public class ExcelMerchant {

    @ExcelVOAttribute(name = "商家姓名", column = "A")
    private String merchantName;

    @ExcelVOAttribute(name = "商家电话", column = "B")
    private String phone;

    @ExcelVOAttribute(name = "登录名", column = "G")
    private String loginName;
//    @ExcelVOAttribute(name = "商家类型", column = "F")
    private Integer type;
    @ExcelVOAttribute(name = "商家类型", column = "F")
    private String typeStr;

    @ExcelVOAttribute(name = "商家LOGO图片", column = "E")
    private String logo;

    @TableField("lon_and_lat")
    @ExcelVOAttribute(name = "商家经纬度", column = "D")
    private String lonAndLat;

    private String describe;

    @ExcelVOAttribute(name = "商家地址", column = "C")
    private String address;
}
