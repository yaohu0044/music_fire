package com.musicfire.modular.machine.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
@TableName("machine_position")
@Data
public class MachinePosition {

    private Integer id;
    /**
     * 机器Id
     */
    @TableField("machine_id")
    private Integer machineId;
    /**
     * 仓口状态
     */
    private Integer state;
    /**
     * 是否默认（false:非，true:默认）
     */
    private Boolean flag;

    private Integer num;
    private Integer commodityId;


}
