package com.musicfire.modular.machine.dto;

import com.musicfire.modular.machine.entity.MachinePosition;
import lombok.Data;

@Data
public class MachinePositionDto extends MachinePosition {
    private String machineName;
    private String machineCode;
    private String merchantName;
    private String commodityName;
}
