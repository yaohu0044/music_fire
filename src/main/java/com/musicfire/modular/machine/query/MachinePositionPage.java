package com.musicfire.modular.machine.query;

import com.musicfire.common.utiles.BasePage;
import lombok.Data;

@Data
public class MachinePositionPage extends BasePage {
    private Integer machineId;
    private String machineName;
    private String machineCode;
}
