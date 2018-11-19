package com.musicfire.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Sommer Jiang
 * 2017-07-03 01:31
 */
@Data
@Component
@ConfigurationProperties(prefix = "alipay")
public class AliPayConfig {


    private String open_api_domain;

    private String mcloud_api_domain;

    private String uid;

    private String appid;

    private String private_key;

    private String public_key;

    private String alipay_public_key;

    private String sign_type;

    private String AES;

    private String return_url;

    private String notify_url;

    private  String CHARSET;



}
