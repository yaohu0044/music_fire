package com.musicfire.modular.room.entity;

import java.util.Date;
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
@TableName("room")
public class Room extends Model<Room> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 房间名
     */
    private String name;
    /**
     * 机器Id
     */
    @TableField("machine_id")
    private Integer machineId;
    /**
     * 商家Id
     */
    @TableField("merchant_id")
    private Integer merchantId;
    /**
     * 房间描述
     */
    private String describe;
    /**
     * false:未删除，true 删除
     */
    private Boolean flag;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 创建人
     */
    @TableField("create_id")
    private Integer createId;
    @TableField("update_time")
    private Date updateTime;
    @TableField("update_id")
    private Integer updateId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMachineId() {
        return machineId;
    }

    public void setMachineId(Integer machineId) {
        this.machineId = machineId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Room{" +
        ", id=" + id +
        ", name=" + name +
        ", machineId=" + machineId +
        ", merchantId=" + merchantId +
        ", describe=" + describe +
        ", flag=" + flag +
        ", createTime=" + createTime +
        ", createId=" + createId +
        ", updateTime=" + updateTime +
        ", updateId=" + updateId +
        "}";
    }
}
