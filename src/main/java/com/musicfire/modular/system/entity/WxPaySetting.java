package com.musicfire.modular.system.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2018-11-11
 */
@TableName("wx_pay_setting")
@Data
public class WxPaySetting  {



    private Integer id;
    /**
     * 公众账号ID
     */
    @TableField("appid")
    private String appId;
    /**
     * 商户号
     */
    @TableField("mch_id")
    private String mchId;
    /**
     * 秘钥key
     */
    private String key;
    @TableField("create_time")
    private Date createTime;
    @TableField("user_id")
    private Integer userId;

}
