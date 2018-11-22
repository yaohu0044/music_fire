package com.musicfire.mobile.controller;


import com.musicfire.common.config.redisdao.RedisDao;
import com.musicfire.mobile.service.IWeChatMpUserService;
import com.musicfire.mobile.wxpay.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/wxpay")
@Slf4j
public class WxPayController {


    private final RedisDao redisDao;




    private final IWeChatMpUserService weChatMpUserService;


    @Autowired
    public WxPayController(RedisDao redisDao,IWeChatMpUserService weChatMpUserService) {
        this.redisDao = redisDao;
        this.weChatMpUserService = weChatMpUserService;
    }



    @ResponseBody
    @PostMapping("wx_prepay/{ids}")
    public Map<String, String> wxPay(@PathVariable List<Integer> ids, HttpServletRequest request) {
        return weChatMpUserService.wxPay(ids, request);
    }

    @ResponseBody
    @RequestMapping("notify")
    public String appPayNotify(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/xml");
            InputStream in = request.getInputStream();
            Map<String, String> map = WXPayUtil.doXMLParseInputStream(in);
            String return_code = map.get("return_code");
            String openid = map.get("openid");
            String out_trade_no = map.get("out_trade_no");
            String transaction_id = map.get("transaction_id");
            String total_fee = map.get("total_fee");
            String time_end = map.get("time_end");
            String is_subscribe = map.get("is_subscribe");
            String body =map.get("body");
            log.info("状态码：" + return_code);
            log.info("用户ID：" + openid);
            log.info("商家订单：" + out_trade_no);
            log.info("微信订单：" + transaction_id);
            log.info("支付金额：" + total_fee + " 分");
            log.info("结束时间：" + time_end);
            log.info("是否关注：" + is_subscribe);
            log.info("内容：" + body);
            log.info(" wxshop charge callback, orderid:{}, result_code:{}, MAP:{}", out_trade_no, return_code, map);
            if (StringUtils.isBlank(out_trade_no)) {
                return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[订单不存在]]></return_msg></xml>";
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
            return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[解析错误]]></return_msg></xml>";
        }
        return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }
}
