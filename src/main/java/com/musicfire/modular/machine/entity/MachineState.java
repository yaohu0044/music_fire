package com.musicfire.modular.machine.entity;


import com.musicfire.modular.machine.machine_enum.MachineStatusEnum;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MachineState {

    String machineCode;
    String netType;
    String cabinetState;
    MachineStatusEnum machineState;
    String updateTime;




}
