package com.musicfire.mobile.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.musicfire.common.businessException.BusinessException;
import com.musicfire.common.businessException.ErrorCode;
import com.musicfire.common.config.WechatAccountConfig;
import com.musicfire.common.utiles.IpUtil;
import com.musicfire.common.utiles.UUIDTool;
import com.musicfire.mobile.dao.WeChatMpUserMapper;
import com.musicfire.mobile.entity.WeChatMpUser;
import com.musicfire.mobile.service.IWeChatMpUserService;
import com.musicfire.mobile.wxpay.HttpUtils;
import com.musicfire.mobile.wxpay.WXPayConstants;
import com.musicfire.mobile.wxpay.WXPayUtil;
import com.musicfire.modular.order.entity.Order;
import com.musicfire.modular.order.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.musicfire.mobile.wxpay.WXPayConstants.DOMAIN_API;
import static com.musicfire.mobile.wxpay.WXPayConstants.UNIFIEDORDER_URL_SUFFIX;

/**
 * <p>
 * 用户微信号 服务实现类
 * </p>
 *
 * @author author
 * @since 2018-11-22
 */
@Slf4j
@Service
public class WeChatMpUserServiceImpl extends ServiceImpl<WeChatMpUserMapper, WeChatMpUser> implements IWeChatMpUserService {

    @Resource
    private IOrderService orderService;

    @Resource
    private WechatAccountConfig accountConfig;

    @Override
    public WeChatMpUser queryByOpenId(String openId) {
        return null;
    }

    @Override
    public Map<String,String> wxPay(String unifiedNum, HttpServletRequest request) {
        EntityWrapper<Order> orderEntityWrapper = new EntityWrapper<>();
        orderEntityWrapper.eq("unified_num",unifiedNum);
        List<Order> orders = orderService.selectList(orderEntityWrapper);
        BigDecimal amount = orders.stream().map(Order::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        try {
            Map<String, String> payMap = new TreeMap<>();
            Map<String, String> paraMap = new HashMap<>();
            paraMap.put("body", "微信支付");
            paraMap.put("out_trade_no", unifiedNum);
            paraMap.put("spbill_create_ip", IpUtil.getIpAddr(request));
            paraMap.put("openid", accountConfig.getOpenAppId());
            paraMap.put("total_fee", WXPayUtil.getMoney(amount));
            log.info("paramap:{}",paraMap);
            String xmlStr =  unifiedOrder(paraMap);
            String prepay_id = "";
            String nonce_str = "";
            String sign = "";
            if (xmlStr.contains("SUCCESS")) {
                Map<String, String> map = WXPayUtil.doXMLParse(xmlStr);
                log.info(" get doXMLParse:{}", map);
                prepay_id = MapUtils.getString(map, "prepay_id");
                nonce_str = MapUtils.getString(map, "nonce_str");
                sign = MapUtils.getString(map, "sign");
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
            throw new BusinessException(ErrorCode.ORDER_VERIFICATION_ERR);
        }
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
}
