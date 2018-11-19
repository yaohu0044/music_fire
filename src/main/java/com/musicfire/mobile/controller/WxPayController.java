package com.musicfire.mobile.controller;


import com.musicfire.common.config.WechatAccountConfig;
import com.musicfire.common.config.redisdao.RedisConstant;
import com.musicfire.common.config.redisdao.RedisDao;
import com.musicfire.common.utiles.IpUtil;
import com.musicfire.mobile.wxpay.HttpUtils;
import com.musicfire.mobile.wxpay.WXPayConstants;
import com.musicfire.mobile.wxpay.WXPayUtil;
import com.musicfire.modular.order.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static com.musicfire.mobile.wxpay.WXPayConstants.DOMAIN_API;
import static com.musicfire.mobile.wxpay.WXPayConstants.UNIFIEDORDER_URL_SUFFIX;


@Controller
@RequestMapping("/wxpay")
@Slf4j
public class WxPayController {

    private final RedisDao redisDao;


    private final WechatAccountConfig accountConfig;

    @Autowired
    public WxPayController(RedisDao redisDao, WechatAccountConfig accountConfig) {
        this.redisDao = redisDao;
        this.accountConfig = accountConfig;
    }

    private  String unifiedOrder(Map<String, String> params) throws Exception {
        String requestUrl = "https://"+ DOMAIN_API+UNIFIEDORDER_URL_SUFFIX;
        Map<String, String> paraMap = new HashMap<>(params);
        paraMap.put("appid", accountConfig.getMpAppId());
        paraMap.put("mch_id", accountConfig.getMchId());
        paraMap.put("nonce_str", WXPayUtil.create_nonce_str());
        paraMap.put("trade_type", "JSAPI");
        paraMap.put("notify_url", accountConfig.getNotifyUrl());
        log.info("para:{}",paraMap);
        String sign = WXPayUtil.generateSignature(paraMap,accountConfig.getMchKey(), WXPayConstants.SignType.MD5);
        paraMap.put("sign", sign);
        String xml = WXPayUtil.mapToXml(paraMap);
        return HttpUtils.post(requestUrl, xml);
    }

    @ResponseBody
    @PostMapping("wx_prepay")
    public Map<String, String> nextSingle(HttpServletRequest request, String orderId) {
        try {
            if(StringUtils.isEmpty(orderId)){
                log.error("orderId is null");
                return null;
            }
            Order order = redisDao.get(RedisConstant.ORDER_PRE+orderId,Order.class);
            if(order!=null){
                log.info("order:{}",order);
            }else {
                log.error("order is null");
                return null;
            }
            Map<String, String> payMap = new TreeMap<> ();
            String openId = (String) request.getSession().getAttribute("openId");
            if (StringUtils.isBlank(openId)) {
                log.error("openId is null");
                return null;
            }
            Map<String, String> paraMap = new HashMap<>();
            paraMap.put("body", "微信支付");
            paraMap.put("out_trade_no", WXPayUtil.createOrderNo());
            paraMap.put("spbill_create_ip", IpUtil.getIpAddr(request));
//            paraMap.put("openid", openId);
            log.info("paramap:{}",paraMap);
            String xmlStr =  unifiedOrder(paraMap);
            String prepay_id = "", nonce_str = "";
            if (xmlStr.contains("SUCCESS")) {
                Map<String, String> map = WXPayUtil.doXMLParse(xmlStr);
                log.info(" get doXMLParse:{}", map);
            } else {
                log.warn("=== 支付错误 failed! ===");
            }
            payMap.put("appId", accountConfig.getMpAppId());
            payMap.put("timeStamp", WXPayUtil.createTimestamp());
            payMap.put("nonceStr", nonce_str);
            payMap.put("signType", "MD5");
            payMap.put("package", "prepay_id=" + prepay_id);
            String paySign = WXPayUtil.generateSignature(payMap,accountConfig.getMchKey(), WXPayConstants.SignType.MD5);
            payMap.put("pg", prepay_id);
            payMap.put("paySign", paySign);
            payMap.put("body", "");
            log.info("===  unifiedOrder :{}  ===", payMap);
            return payMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
