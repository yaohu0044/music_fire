package com.musicfire.mobile.controller;


import com.musicfire.common.config.ProjectUrlConfig;
import com.musicfire.common.utiles.Constant;
import com.musicfire.mobile.entity.WeChatMpUser;
import com.musicfire.mobile.service.IWeChatMpUserService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

/**
 * Created by Sommer Jiang
 * 2017-07-03 01:20
 */
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    private final WxMpService wxMpService;

    private final ProjectUrlConfig projectUrlConfig;

    private final IWeChatMpUserService weChatMpUserService;

    @Autowired
    public WechatController(WxMpService wxMpService, ProjectUrlConfig projectUrlConfig, IWeChatMpUserService weChatMpUserService) {
        this.wxMpService = wxMpService;
        this.projectUrlConfig = projectUrlConfig;
        this.weChatMpUserService = weChatMpUserService;
    }

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) {
        //1. 配置
        //2. 调用方法
        String url = projectUrlConfig.getWechatMpAuthorize() + "/wechat/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_USER_INFO, URLEncoder.encode(returnUrl));
        log.info("redirectUrl:{}",redirectUrl);
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/userInfo")
    public String userInfo(HttpSession session, @RequestParam("code") String code,
                           @RequestParam("state") String returnUrl) {
       WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);

            WxMpUser mpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken,null);
            log.info("mpuser:{}",mpUser);
            if(mpUser!=null){
                WeChatMpUser weChatMpUser =  weChatMpUserService.queryByOpenId(mpUser.getOpenId());
              if(weChatMpUser==null){
                  weChatMpUser = new WeChatMpUser();
                  weChatMpUser.setOpenId(mpUser.getOpenId());
                  weChatMpUser.setCity(mpUser.getCity());
                  weChatMpUser.setCountry(mpUser.getCountry());
                  weChatMpUser.setHeadImgUrl(mpUser.getHeadImgUrl());
                  weChatMpUser.setNickname(mpUser.getNickname());
                  BeanUtils.copyProperties(mpUser,weChatMpUser);
                  weChatMpUserService.insert(weChatMpUser);
              }
            }

        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}", e);
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        session.setAttribute(Constant.WECHAT_OPEN_ID,openId);
        return "redirect:" + returnUrl;
    }
}
