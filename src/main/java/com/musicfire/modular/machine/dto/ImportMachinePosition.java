package com.musicfire.modular.machine.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.musicfire.common.utiles.ExcelVOAttribute;
import lombok.Data;

@Data
public class ImportMachinePosition {

    /**
     * 机器Id
     */
    @ExcelVOAttribute(name = "机器Code", column = "A")
    private String machineCode;

    @ExcelVOAttribute(name = "商品Id", column = "B")
    private Integer commodityId;
    @ExcelVOAttribute(name = "仓位号", column = "c")
    private Integer num;
}
