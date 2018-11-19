package com.musicfire.modular.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2018-11-11
 */
@TableName("ali_pay_setting")
@Data
public class AliPaySetting {

    private Integer id;
    @TableField("appid")
    private String appId;

    private String uid;

    @TableField("private_key")
    private String privateKey;
    @TableField("public_key")
    private String publicKey;


}
