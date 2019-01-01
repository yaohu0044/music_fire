package com.musicfire.modular.merchant.dto;

import com.musicfire.common.utiles.ExcelVOAttribute;
import lombok.Data;

@Data
public class ImportMerchant {


    @ExcelVOAttribute(name = "商家姓名", column = "A")
    private String merchantName;

    /**
     * 商家登录名
     */
    @ExcelVOAttribute(name = "商家登录名", column = "B")
    private String loginName;
    /**
     * 商家密码
     */
    @ExcelVOAttribute(name = "商家密码", column = "C")
    private String password;

    /**
     * 商家类型
     */
    @ExcelVOAttribute(name = "type", column = "D")
    private Integer type;


    /**
     * 商家logo
     */
    @ExcelVOAttribute(name = "logo", column = "E")
    private String logo;

    /**
     * 经纬度
     */
    @ExcelVOAttribute(name = "lonAndLat", column = "F")
    private String lonAndLat;

    /**
     * 描述
     */
    @ExcelVOAttribute(name = "描述", column = "G")
    private String describe;

    /**
     * 地址
     */
    @ExcelVOAttribute(name = "address", column = "H")
    private String address;

    @ExcelVOAttribute(name = "phone", column = "I")
    private String phone;

}
