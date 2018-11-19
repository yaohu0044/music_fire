package com.musicfire.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Sommer Jiang
 * 2017-07-30 11:43
 */
@Data
@Component
public class ProjectUrlConfig {

    /**
     * 微信公众平台授权url
     */
    @Value("${projectUrl.wechatMpAuthorize}")
    public String wechatMpAuthorize;

    /**
     * 微信开放平台授权url
     */
    @Value("${projectUrl.wechatOpenAuthorize}")
    public String wechatOpenAuthorize;

    /**
     * 贩卖系统
     */
    @Value("${projectUrl.vendingmachine}")
    public String vendingmachine;

    @Value("${projectUrl.mobile}")
    public String mobile;


}
