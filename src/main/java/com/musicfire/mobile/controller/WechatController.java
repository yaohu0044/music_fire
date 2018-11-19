package com.musicfire.mobile.controller;


import com.musicfire.common.config.ProjectUrlConfig;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
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

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxMpService wxOpenService;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;


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

//              WechatMpUser wechatMpUser =  getWeiXinUserInfoService.getWechatMpUserByOpenId(mpUser.getOpenId());
//              if(wechatMpUser==null){
//                  wechatMpUser = new WechatMpUser();
//
////                  wechatMpUser.setOpenId(mpUser.getOpenId());
////                  wechatMpUser.setCity(mpUser.getCity());
////                  wechatMpUser.setCountry(mpUser.getCountry());
////                  wechatMpUser.setHeadImgUrl(mpUser.getHeadImgUrl());
////                  wechatMpUser.setNickname(mpUser.getNickname());
//                  BeanCopy.copyBeanObject(wechatMpUser,mpUser);
//                  wechatMpUser.setId(UuidUtil.getUUID());
//                  wechatMpUser.setIsAdmin(0);
//                  getWeiXinUserInfoService.restoreWxMpUser(wechatMpUser);
//              }
            }

        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}", e);
//            throw new VendingMachineException(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }

        String openId = wxMpOAuth2AccessToken.getOpenId();
        session.setAttribute("openId",openId);
        return "redirect:" + returnUrl;
    }
}
