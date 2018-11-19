package com.sommer.mobile.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.musicfire.common.config.AliPayConfig;
import com.musicfire.common.config.ProjectUrlConfig;
import com.musicfire.common.config.redisdao.RedisConstant;
import com.musicfire.common.config.redisdao.RedisDao;
import com.musicfire.mobile.service.AlipayService;
import com.musicfire.modular.order.entity.Order;
import com.musicfire.modular.system.entity.AlipayUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@Controller
@RequestMapping("/alipay")
@Slf4j
public class AliPayController {

    @Autowired
    AliPayConfig aliPayConfig;

    @Autowired
    RedisDao redisDao;

    @Autowired
    AlipayService alipayService;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    /**
     * 跳转到授权界面
     */
    @GetMapping("/authorize")
    public String authorize(String code) {

        //页面回调地址 必须与应用中的设置一样
        String return_url = projectUrlConfig.vendingmachine+"/alipay/userInfo";
        //回调地址必须经encode
        return_url = java.net.URLEncoder.encode(return_url);

        //重定向到授权页面

        String redirectUrl = "https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id=" + aliPayConfig.getAppid()
                + "&scope=auth_user&redirect_uri=" + return_url + "&state=" + code;
        log.info("redirectUrl:{}", redirectUrl);


        return "redirect:" + redirectUrl;
    }


    @GetMapping("/notify")
    public String notifyValue(HttpServletRequest request) throws UnsupportedEncodingException {


        return "redirect:/mobile/machine?code=" + "12345678";
    }

    @RequestMapping(value = "/userInfo")
    public String getAlipayAuth(String auth_code, HttpSession session, Model model, String state) {


        log.info("state:{}", state);
        AlipayClient alipayClient = new DefaultAlipayClient(aliPayConfig.getOpen_api_domain(),
                aliPayConfig.getAppid(),
                aliPayConfig.getPrivate_key(),
                "json",
                "utf-8",
                aliPayConfig.getAlipay_public_key(),
                aliPayConfig.getSign_type());

//        log.info(alipayClient.toString()+"   "+aliPayConfig.getAppid());

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
                AlipayUserInfoShareResponse userinfoShareResponse = alipayClient.execute(requestUser, oauthTokenResponse.getAccessToken());
                if (userinfoShareResponse.isSuccess()) {
                    log.info(userinfoShareResponse.getBody());
                    log.info("调用成功");
                    log.info("UserId:" + userinfoShareResponse.getUserId() + "\n");//用户支付宝ID
                    log.info("NickName:" + userinfoShareResponse.getNickName() + "\n");//用户支付宝昵称
//                    log.info("UserType:" + userinfoShareResponse.getUserType() + "\n");//用户类型
//                    log.info("UserStatus:" + userinfoShareResponse.getUserStatus() + "\n");//用户账户动态
//                    log.info("Email:" + userinfoShareResponse.getEmail() + "\n");//用户Email地址
//                    log.info("IsCertified:" + userinfoShareResponse.getIsCertified() + "\n");//用户是否进行身份认证
//                    log.info("IsStudentCertified:" + userinfoShareResponse.getIsStudentCertified() + "\n");//用户是否进行学生认证
//                    AlipayUserInfo aliPayUserInfo = new AlipayUserInfo();
                    session.setAttribute("aliId",userinfoShareResponse.getUserId() );
                    AlipayUserInfo userInfo = new AlipayUserInfo();
                    BeanUtils.copyProperties(userinfoShareResponse, userInfo);
                    alipayService.saveAlipayUser(userInfo);
//                    redisDao.add(RedisConstant.ALIPAY_PRE+aliPayUserInfo.getUserId(),aliPayUserInfo);

                } else {
                    log.info("调用失败");
                    return "mobile/error";
                }

            } catch (AlipayApiException e) {
                //处理异常
                e.printStackTrace();
                return "mobile/error";
            }
        } catch (AlipayApiException e) {
            //处理异常
            e.printStackTrace();
            return "mobile/error";
        }

//        String code = (String) session.getAttribute("code");
//        log.info("code:{}",code);
//        model.addAttribute("code",code);
        return "redirect:/mobile/machine";


    }

    @RequestMapping(value = "/return")
    public String alipayReturn(HttpServletRequest request,HttpSession session,RedirectAttributes attr) throws UnsupportedEncodingException, AlipayApiException {

//        PrintWriter out = response.getWriter();
        //获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号

        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

        //支付宝交易号

        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        //计算得出通知验证结果

        boolean verify_result = AlipaySignature.rsaCheckV1(params, aliPayConfig.getAlipay_public_key(), aliPayConfig.getCHARSET(), "RSA2");

        if (verify_result) {//验证成功
            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码
            //该页面可做页面美工编辑
//            out.clear();
//            out.println("success<br />");
            String orderid = (String) session.getAttribute("orderid");
            attr.addAttribute("orderid",orderid);
            return "redirect:/order/paySuccess" ;


            //////////////////////////////////////////////////////////////////////////////////////////
        } else {
            //该页面可做页面美工编辑
//            out.clear();
//            out.println("failed");
        }
        return null;
    }

    @RequestMapping(value = "/notify")
    public String alipayNotify(HttpServletRequest request, PrintWriter out) throws UnsupportedEncodingException, AlipayApiException {
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号

        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
        //支付宝交易号

        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        //计算得出通知验证结果
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        boolean verify_result = AlipaySignature.rsaCheckV1(params, aliPayConfig.getAlipay_public_key(), aliPayConfig.getCHARSET(), "RSA2");

        if (verify_result) {//验证成功
            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码

            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

            if (trade_status.equals("TRADE_FINISHED")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                //如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
            } else if (trade_status.equals("TRADE_SUCCESS")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
            }

            //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

            out.println("success");    //请不要修改或删除

            //////////////////////////////////////////////////////////////////////////////////////////
        } else {//验证失败
            out.println("fail");
        }

        return null;
    }

    @RequestMapping(value = "/pay")
    @ResponseBody
    public String alipayPay(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {
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
        alipayRequest.setReturnUrl(aliPayConfig.getReturn_url());
        //设置支付宝异步通知地址
        alipayRequest.setNotifyUrl(aliPayConfig.getNotify_url());
        //以下为用户请求参数，此处为接收来自前台的表单提交的参数
        //商户订单号，商户网站订单系统中唯一订单号，必填

        String out_trade_no = new String(request.getParameter("WIDout_trade_no ").getBytes("ISO-8859-1"), "UTF-8");
        //付款金额，必填
        String total_amount = new String(request.getParameter("WIDtotal_amount ").getBytes("ISO-8859-1"), "UTF-8");
        //交易标题，必填
        String subject = new String(request.getParameter("WIDsubject ").getBytes("ISO-8859-1"), "UTF-8");
        //交易描述，可空
        String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"), "UTF-8");

        //业务请求参数的集合，最大长度不限，除公共参数外所有请求参数都必须放在这个参数中传递
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"timeout_express\":\"30m\","//该笔订单允许的最晚付款时间，逾期将关闭交易。
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");//销售产品码，与支付宝签约的产品码名称。 注：目前仅支持FAST_INSTANT_TRADE_PAY

        //发送请求，支付宝将返回一个支付请求的表单数据串
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        //将返回结果输出至页面，将再次向支付宝发起请求，此次请求将直接跳转至支付宝支付页面
//        out.println(result);//此处为jsp页面的输出方式
        return null;

    }


    @RequestMapping(value = "/tradePay")
    public String tradePay(Map<String, Object> map, HttpServletResponse response, String orderid,HttpSession session) throws IOException {

        if (StringUtils.isEmpty(orderid)) {
            log.error("orderid is null");
            return null;
        }
        Order order = redisDao.get(RedisConstant.ORDER_PRE + orderid, Order.class);
        if (order != null) {
            log.info("order:{}", order);
        } else {
            log.error("order is null");
            return null;
        }
        session.setAttribute("orderid",orderid);
//        OrderDto order = new OrderDto();
//        order.setAmount(new BigDecimal(0.01));
//        order.setDescription("西瓜 0.01元");
        String result = alipayService.tradePay(order);
        map.put("result", result);
        response.setContentType("text/html;charset=" + aliPayConfig.getCHARSET());
        response.getWriter().write(result);//直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();
        return "mobile/user/alipay_info";
    }
}
