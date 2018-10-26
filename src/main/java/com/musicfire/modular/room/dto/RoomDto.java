package com.musicfire.modular.room.dto;

import com.musicfire.common.validated.Insert;
import com.musicfire.common.validated.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("房间信息")
public class RoomDto {

    @NotNull(groups = {Update.class},message = "Id不能为空")
    private Integer id;
    /**
     * 房间名
     */

    @ApiModelProperty("房间名")
    @NotBlank(groups = {Update.class,Insert.class},message="房间名不能为空")
    private String name;
    /**
     * 机器Id
     */
    @ApiModelProperty("机器Id")
    @Min(value = 1,message="机器Idz最小为1")
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

}
