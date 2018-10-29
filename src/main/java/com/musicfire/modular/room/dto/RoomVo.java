package com.musicfire.modular.room.dto;

import com.musicfire.common.validated.Insert;
import com.musicfire.common.validated.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@ApiModel("房间信息")
public class RoomVo {

    @NotNull(groups = {Update.class},message = "Id不能为空")
    private Integer id;
    /**
     * 房间名
     */

    @ApiModelProperty(name = "房间名",value = "房间1")
    @NotBlank(groups = {Update.class,Insert.class},message="房间名不能为空")
    private String name;
    /**
     * 机器Id
     */
    @ApiModelProperty("机器Id")
    @Min(value = 1,message="机器Id最小为1")
    @NotNull(groups = {Update.class,Insert.class},message = "机器id不能为空")
    private Integer machineId;
    /**
     * 商家Id
     */
    @ApiModelProperty("商家Id")
    @Min(value = 1,message="商家Id最小为1")
    @NotNull(groups = {Update.class,Insert.class},message = "商家id不能为空")
    private Integer merchantId;
    /**
     * 房间描述
     */
    private String describe;

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
}
