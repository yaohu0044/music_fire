package com.musicfire.modular.machine.dto;

import com.musicfire.modular.machine.entity.Machine;
import lombok.Data;

@Data
public class MachineDto extends Machine {

    private String merchantName;

    private String stateStr;

    /**
     * 机器仓位状态
     */
    private String machinePositionStr;
}
