package com.musicfire.modular.machine.dto;

import com.musicfire.common.utiles.ExcelVOAttribute;
import lombok.Data;

@Data
public class ImportMachine {

    /**
     * 1:WiFi版 2:GPRS版
     */
    @ExcelVOAttribute(name = "机器类型（1：wifi版，2：gprs版）", column = "A")
    private Integer type;
    /**
     * 机器名
     */
    @ExcelVOAttribute(name = "机器名称", column = "B")
    private String name;
    /**
     * 机器code
     */
    @ExcelVOAttribute(name = "机器code", column = "C")
    private String code;

}
