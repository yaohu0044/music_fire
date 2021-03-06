package com.musicfire.modular.merchant.dto;

import com.musicfire.common.validated.Insert;
import com.musicfire.common.validated.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class MerchantVo {

    @NotNull(groups = {Update.class},message = "Id不能为空")
    private Integer id;

    private String merchantName;

    /**
     * 商家登录名
     */
    @NotBlank(groups = {Update.class, Insert.class},message="登录名")
    private String loginName;
    /**
     * 商家密码
     */
    @NotBlank(groups = {Update.class, Insert.class},message="密码")
    private String password;
    /**
     * 商家title
     */
    private String title;

    /**
     * 商家类型
     */
    @NotNull(groups = {Update.class, Insert.class},message="商家类型")
    private Integer type;


    /**
     * 商家logo
     */
    @NotBlank(groups = {Update.class, Insert.class},message="商家logo")
    private String logo;

    /**
     * 经纬度
     */
    @NotBlank(groups = {Update.class, Insert.class},message="经纬度")
    private String lonAndLat;

    /**
     * 描述
     */
    private String describe;

    /**
     * 地址
     */
    @NotBlank(groups = {Update.class, Insert.class},message="地址")
    private String address;

    @NotBlank(groups = {Update.class, Insert.class},message="电话")
    private String phone;

    private Integer userId;

    //商家Id. 在给代理分配商家时使用
    private List<Long> merchantId;

}
