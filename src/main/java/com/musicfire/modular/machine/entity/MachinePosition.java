package com.musicfire.modular.machine.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author author
 * @since 2018-10-25
 */
@TableName("machine_position")
public class MachinePosition extends Model<MachinePosition> {

    private static final long serialVersionUID = 1L;

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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "MachinePosition{" +
                ", id=" + id +
                ", machineId=" + machineId +
                ", state=" + state +
                ", default=" + flag +
                "}";
    }
}
