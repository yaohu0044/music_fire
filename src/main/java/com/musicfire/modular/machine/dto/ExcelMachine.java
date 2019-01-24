package com.musicfire.modular.machine.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.musicfire.common.utiles.ExcelVOAttribute;
import lombok.Data;

@Data
public class ExcelMachine {

    @ExcelVOAttribute(name = "商家名字", column = "A")
    private String merchantName;


    @ExcelVOAttribute(name = "机器状态", column = "C")
    private String stateStr;

    /**
     * 1:WiFi版 2:GPRS版
     */
    @ExcelVOAttribute(name = "机器类型（1：wifi版，2：gprs版）", column = "D")
    private Integer type;
    /**
     * 机器名
     */
    @ExcelVOAttribute(name = "机器名称", column = "E")
    private String name;
    /**
     * 机器code
     */
    @ExcelVOAttribute(name = "机器code", column = "F")
    private String code;
    /**
     * 经纬度
     */
    @ExcelVOAttribute(name = "机器所在经纬度", column = "G")
    private String lonAndLat;

    /**
     * 是否已分配房间
     */
    @ExcelVOAttribute(name = "是否分配房间", column = "H")
    private Boolean distribution;

    /**
     * 商家Id
     */
    @ExcelVOAttribute(name = "商家Id", column = "I")
    private Integer merchantId;


    /**
     * 商家Id
     */
    @ExcelVOAttribute(name = "机器ID", column = "J")
    private Integer id;
}
