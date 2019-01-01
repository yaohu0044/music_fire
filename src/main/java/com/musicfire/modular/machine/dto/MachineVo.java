package com.musicfire.modular.machine.dto;

import com.musicfire.common.validated.Insert;
import com.musicfire.common.validated.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MachineVo {

    @NotNull(groups = {Update.class},message = "Id不能为空")
    private Integer id;
    /**
     * 机器名
     */
    @NotBlank(groups = {Update.class, Insert.class},message="机器名不能为空")
    private String name;
    /**
     * 机器code
     */
    @NotBlank(groups = {Update.class,Insert.class},message="机器code不能为空")
    private String code;
    /**
     * 经纬度
     */
    private String lonAndLat;
    /**
     * 商家id
     */
    private Integer merchantId;

    private String address;

    /**
     * 二维码
     */
    private String qrCodeUrl;
}
