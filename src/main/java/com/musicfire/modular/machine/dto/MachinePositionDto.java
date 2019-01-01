package com.musicfire.modular.machine.dto;

import com.musicfire.modular.machine.entity.MachinePosition;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MachinePositionDto extends MachinePosition {
    private String machineName;
    private String machineCode;
    private String merchantName;
    private String commodityName;
    /**
     * 缩率图
     */

    private String shrinkageChart;
    /**
     * 价格
     */
    private BigDecimal price;

    private Integer commodityId;

    private String commodityDes;

    private Integer machineState;
}
