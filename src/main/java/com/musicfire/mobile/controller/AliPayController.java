package com.musicfire.mobile.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.musicfire.common.businessException.BusinessException;
import com.musicfire.common.businessException.ErrorCode;
import com.musicfire.common.config.AliPayConfig;
import com.musicfire.common.config.ProjectUrlConfig;
import com.musicfire.common.config.redisdao.RedisDao;
import com.musicfire.common.utiles.Constant;
import com.musicfire.mobile.entity.AliPayUserInfo;
import com.musicfire.mobile.service.AliPayService;
import com.musicfire.modular.order.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
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

    @RequestMapping(value = "/notify")
    public String aliPayNotify(HttpServletRequest request, PrintWriter out) throws UnsupportedEncodingException, AlipayApiException {
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Object o : requestParams.keySet()) {
            String name = (String) o;
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            valueStr = new String(valueStr.getBytes(StandardCharsets.ISO_8859_1), "gbk");
            params.put(name, valueStr);
        }
        //商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1
        ), StandardCharsets.UTF_8);
        //支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
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
        AlipayClient alipayClient = new DefaultAlipayClient(aliPayConfig.getOpen_api_domain(),
                aliPayConfig.getAppid(),
                aliPayConfig.getPrivate_key(),
                "json",
                "utf-8",
                aliPayConfig.getAlipay_public_key(),
                aliPayConfig.getSign_type());
        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        //设置支付宝同步通知地址
//        alipayRequest.setReturnUrl(aliPayConfig.getReturn_url());
        //设置支付宝异步通知地址
        alipayRequest.setNotifyUrl(aliPayConfig.getNotify_url());

        //商户订单号，商户网站订单系统中唯一订单号，必填
//		String out_trade_no = unifiedNum;
        //付款金额，必填
        String total_amount = reduce.toString();
        //交易标题，必填
        String subject = "好东西";
        //交易描述，可空
        String body = "";

        //业务请求参数的集合，最大长度不限，除公共参数外所有请求参数都必须放在这个参数中传递
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + unifiedNum + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"timeout_express\":\"30m\","//该笔订单允许的最晚付款时间，逾期将关闭交易。
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");//销售产品码，与支付宝签约的产品码名称。 注：目前仅支持FAST_INSTANT_TRADE_PAY

        //发送请求，支付宝将返回一个支付请求的表单数据串
        String result;
        try {
            result = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.PAY_ERR);
        }
        return  result;
    }

}
