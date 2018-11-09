package com.musicfire.modular.machine.dto;

import com.musicfire.common.validated.Insert;
import com.musicfire.common.validated.Update;
import com.musicfire.modular.machine.entity.Machine;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MachineDto extends Machine {

    private String merchantName;

}
