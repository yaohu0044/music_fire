package com.musicfire.mobile.service;

import com.baomidou.mybatisplus.service.IService;
import com.musicfire.mobile.entity.WeChatMpUser;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户微信号 服务类
 * </p>
 *
 * @author author
 * @since 2018-11-22
 */
public interface IWeChatMpUserService extends IService<WeChatMpUser> {

    WeChatMpUser queryByOpenId(String openId);


    Map<String,String> wxPay(String unifiedNum, HttpServletRequest request);
}
