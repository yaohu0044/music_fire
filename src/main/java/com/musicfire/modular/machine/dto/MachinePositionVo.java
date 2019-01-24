package com.musicfire.modular.machine.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

@Data
public class MachinePositionVo {
    /**
     * 机器Id
     */
    @TableField("machine_id")
    private Integer machineId;

    private Integer commodityId;
    private Integer num;

    private Integer id;
}
