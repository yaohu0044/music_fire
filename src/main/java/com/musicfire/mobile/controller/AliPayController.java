package com.musicfire.mobile.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.musicfire.common.config.AliPayConfig;
import com.musicfire.common.config.ProjectUrlConfig;
import com.musicfire.common.config.redisdao.RedisDao;
import com.musicfire.common.utiles.Constant;
import com.musicfire.mobile.dto.AliPayCallRequestBody;
import com.musicfire.mobile.entity.AliPayUserInfo;
import com.musicfire.mobile.service.AliPayService;
import com.musicfire.modular.order.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/alipay")
@Slf4j
public class AliPayController {

    private final AliPayConfig aliPayConfig;

    private final AliPayService aliPayService;

    private final ProjectUrlConfig projectUrlConfig;

    private final RedisDao redisDao;

    @Autowired
    public AliPayController(AliPayConfig aliPayConfig, RedisDao redisDao, AliPayService aliPayService, ProjectUrlConfig projectUrlConfig) {
        this.aliPayConfig = aliPayConfig;
        this.aliPayService = aliPayService;
        this.projectUrlConfig = projectUrlConfig;
        this.redisDao = redisDao;
    }


    /**
     * 跳转到授权界面
     */
    @GetMapping("/authorize")
    public String authorize(String code) {

        //页面回调地址 必须与应用中的设置一样
        String return_url = projectUrlConfig.vendingmachine + "/alipay/userInfo";
        //回调地址必须经encode
        return_url = java.net.URLEncoder.encode(return_url);

        //重定向到授权页面

        String redirectUrl = "https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id=" + aliPayConfig.getAppid()
                + "&scope=auth_user&redirect_uri=" + return_url + "&state=" + code;
        log.info("redirectUrl:{}", redirectUrl);


        return "redirect:" + redirectUrl;
    }
    @RequestMapping(value = "/userInfo")
    public String getAliPayAuth(String auth_code, HttpSession session, String state) {


        log.info("state:{}", state);
        AlipayClient alipayClient = new DefaultAlipayClient(aliPayConfig.getOpen_api_domain(),
                aliPayConfig.getAppid(),
                aliPayConfig.getPrivate_key(),
                "json",
                "utf-8",
                aliPayConfig.getAlipay_public_key(),
                aliPayConfig.getSign_type());

        AlipaySystemOauthTokenRequest alipaySystemOauthTokenRequest = new AlipaySystemOauthTokenRequest();

        alipaySystemOauthTokenRequest.setCode(auth_code);

        alipaySystemOauthTokenRequest.setGrantType(
                "authorization_code"
        );

        try {
            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(alipaySystemOauthTokenRequest);
            String accessToken = oauthTokenResponse.getAccessToken();
            String userId = oauthTokenResponse.getUserId();
            log.info("====================userId:" + userId);
            log.info("====================accessToken:" + accessToken);
            //调用接口获取用户信息
            AlipayUserInfoShareRequest requestUser = new AlipayUserInfoShareRequest();
            try {
                AlipayUserInfoShareResponse userInfoShareResponse = alipayClient.execute(requestUser, oauthTokenResponse.getAccessToken());
                if (userInfoShareResponse.isSuccess()) {
                    //判断用户是否已经存在
                    EntityWrapper<AliPayUserInfo> entityWrapper = new EntityWrapper<>();
                    entityWrapper.eq("user_id",userInfoShareResponse.getUserId());
                    List<AliPayUserInfo> aliPayUserInfo = aliPayService.selectList(entityWrapper);
                    AliPayUserInfo userInfo =null;
                    if(null == aliPayUserInfo || aliPayUserInfo.size()==0){
                        userInfo = new AliPayUserInfo();
                        BeanUtils.copyProperties(userInfoShareResponse, userInfo);
                        aliPayService.saveAliPayUser(userInfo);
                    }
                    log.info("调用成功");
                    session.setAttribute(Constant.AI_PAY_USER, userInfo);
                } else {
                    log.info("调用失败");
                    return "redirect:/error.html";
                }
            } catch (AlipayApiException e) {
                //处理异常
                e.printStackTrace();
                return "redirect:/error.html";
            }
        } catch (AlipayApiException e) {
            //处理异常
            e.printStackTrace();
            return "redirect:/error.html";
        }
        return "redirect:/goodsList.html";
    }

    @PostMapping(value = "/notify")
    public String aliPayNotify(HttpServletRequest request, PrintWriter out) throws UnsupportedEncodingException, AlipayApiException {

        Map<String, String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            System.out.println(">>>>>参数" + name + ":" + valueStr);
            params.put(name, valueStr);
        }


       //现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
        // valueStr = new String(valueStr.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        //商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.UTF_8
        ), StandardCharsets.UTF_8);
        //支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        //计算得出通知验证结果
        boolean verify_result = AlipaySignature.rsaCheckV1(params, aliPayConfig.getAlipay_public_key(), aliPayConfig.getCHARSET(), "RSA2");

        if (verify_result) {
            if (trade_status.equals("TRADE_SUCCESS")) {
                aliPayService.saveAliPayOrder(out_trade_no, trade_no, params.get("total_amount"));
            }
            out.println("success");
        } else {//验证失败
            out.println("fail");
        }
        return null;
    }


    public String aliPay(String unifiedNum) {

        List<Order> orders = redisDao.getList(unifiedNum, Order.class);
        BigDecimal reduce = orders.stream().map(Order::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        //获得初始化的AlipayClient,请勿更改参数顺序
        AlipayClient  alipayClient = new DefaultAlipayClient(aliPayConfig.getOpen_api_domain(),
                aliPayConfig.getAppid(),
                aliPayConfig.getPrivate_key(),
                "json",
                "utf-8",
                aliPayConfig.getAlipay_public_key(),
                aliPayConfig.getSign_type());



        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();

        AliPayCallRequestBody aliPayCallRequestBody = new AliPayCallRequestBody();
        aliPayCallRequestBody.setOutTradeNo(unifiedNum);
        aliPayCallRequestBody.setSubject("您的宝贝");
        aliPayCallRequestBody.setTotalAmount(reduce);
        String payRequestStr = JSON.toJSONString(aliPayCallRequestBody);

        request.setBizContent(payRequestStr);

        String notify_url = aliPayConfig.getNotify_url();

        String return_url = aliPayConfig.getReturn_url();

        //设置支付宝回调地址
        request.setReturnUrl(return_url);

        request.setNotifyUrl(notify_url);

        String requestJSONStr = JSON.toJSONString(request.getBizContent());

        AlipayTradeWapPayResponse response = null;
        String rest="";
        try {
            response = alipayClient.pageExecute(request);
            rest=response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return  rest;
    }

}
