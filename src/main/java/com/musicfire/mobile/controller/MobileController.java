package com.musicfire.mobile.controller;

import com.musicfire.common.config.ProjectUrlConfig;
import com.musicfire.common.utiles.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class MobileController {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @Autowired
    private AliPayController aliPayController;

    @Autowired
    private WxPayController wxPayController;

    @RequestMapping(value = "/mobilePay/{unifiedNum}")
    @ResponseBody
    private Map<String,String> mobilePay(@PathVariable String unifiedNum, HttpServletResponse response, HttpServletRequest request){
        String netAgent = IpUtil.getAgent(request);
        if (netAgent.contains("wechat")) {
            String weiXinPay = wxPayController.wxPay(unifiedNum, request);
            Map<String,String> map = new HashMap<>();
            map.put("data",weiXinPay);
            map.put("type",String.valueOf(2));
            return map;
        } else if(netAgent.contains("alipay")) {
            String aliPayStr = aliPayController.aliPay(unifiedNum);
            Map<String,String> map = new HashMap<>();
            map.put("data",aliPayStr);
            map.put("type",String.valueOf(1));
            return map;
        }

        return null;

    }

    @RequestMapping(value = "/mobile")
    private String intoMobile(HttpSession session, HttpServletRequest request, String code, RedirectAttributes attr) {
        String netAgent = IpUtil.getAgent(request);
        System.out.println("开始授权：");
        log.info(netAgent);

        if (!netAgent.equalsIgnoreCase("wechat") && !netAgent.equalsIgnoreCase("alipay"))
            return "mobile/error";
        session.setAttribute("netAgent", netAgent);
        if (netAgent.contains("wechat")) {
            session.setAttribute("type", "wechat");
            session.setAttribute("code", code);
            attr.addAttribute("returnUrl", projectUrlConfig.mobile + "/goodsList.html?code=" + code);

            return "redirect:"+projectUrlConfig.mobile+"/api/wechat/authorize";
        } else if (netAgent.contains("alipay")) {
            session.setAttribute("type", "alipay");
            session.setAttribute("code", code);
            attr.addAttribute("code", code);
            log.info("跳转地址："+projectUrlConfig.mobile+"/api/alipay/authorize?code="+code);
            return "redirect:"+projectUrlConfig.mobile+"/api/alipay/authorize";
        } else {
            return "mobile/error";
        }
    }


    @RequestMapping(value = "/mobile_register")
    private String intoMobileRegister(HttpServletRequest request, String seller, RedirectAttributes attr) {


        String netAgent = IpUtil.getAgent(request);

        log.info(netAgent);
        if (!netAgent.equalsIgnoreCase("wechat") && !netAgent.equalsIgnoreCase("alipay"))
            return "mobile/error";
        attr.addAttribute("returnUrl", projectUrlConfig.mobile + "/register_admin?seller=" + seller);
        return "redirect:wechat/authorize";

    }

    @RequestMapping(value = "/mobile/register_admin")
    private String registerAdmin(HttpSession session, String openid) {

        log.info("openid:{}", openid);
        session.setAttribute("openid", openid);
        return "mobile/admin/register";

    }

    @RequestMapping(value = "/mobile/error")
    private String mobileError() {

        return "mobile/error";

    }



    @RequestMapping(value = "/mobile/machine")
    private String intoMachine(HttpSession session, ModelMap modelMap, HttpServletRequest request) {
        String code = (String) session.getAttribute("code");
        String type = (String) session.getAttribute("type");
        log.info("mobile/machine:{}", type);
        request.setAttribute("code", code);
        if(type.contains("wechat")){
            String openid = (String) session.getAttribute("openId");
            if(StringUtils.isEmpty(openid)){
                modelMap.addAttribute("msg","进入机器模式 OPENID 为空");
                return "mobile/error";
            }
        }
        return "mobile/user/machine_main";
    }


    @RequestMapping(value = "/mobile/cabinetNew")
    private String intoCabinet(HttpSession session, Model model, HttpServletRequest request) {
        return "mobile/shop/cabinet_detail_new";

    }
}
