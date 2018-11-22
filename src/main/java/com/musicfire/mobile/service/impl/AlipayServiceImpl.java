package com.musicfire.mobile.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.musicfire.common.businessException.BusinessException;
import com.musicfire.common.businessException.ErrorCode;
import com.musicfire.common.config.AliPayConfig;
import com.musicfire.common.config.redisdao.RedisConstant;
import com.musicfire.common.config.redisdao.RedisDao;
import com.musicfire.common.utiles.UUIDTool;
import com.musicfire.mobile.dao.AliPayUserInfoMapper;
import com.musicfire.mobile.entity.AliPayUserInfo;
import com.musicfire.mobile.enums.ResultEnum;
import com.musicfire.mobile.service.AliPayService;
import com.musicfire.modular.order.entity.Order;
import com.musicfire.modular.order.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class AliPayServiceImpl extends ServiceImpl<AliPayUserInfoMapper, AliPayUserInfo> implements AliPayService {

    @Resource
    private AliPayConfig aliPayConfig;

    @Resource
    private RedisDao redisDao;

    @Resource
    private IOrderService orderService;


    @Transactional
    public int saveAliPayUser(AliPayUserInfo aliPayUserInfo) {
        int ret = 0;
        if (redisDao.exist(RedisConstant.ALIPAY_PRE + aliPayUserInfo.getUserId())) {
            redisDao.update(RedisConstant.ALIPAY_PRE + aliPayUserInfo.getUserId(), aliPayUserInfo);
        } else {
            redisDao.add(RedisConstant.ALIPAY_PRE + aliPayUserInfo.getUserId(), aliPayUserInfo);
        }
        return ret;
    }

    @Override
    public String aliPayStr(List<Integer> ids) {
        String unifiedNum = UUIDTool.getOrderIdByUUId();//统一订单号
        //总金额
        BigDecimal reduce = orderService.inserAll(ids, unifiedNum);

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
        String result = null;
        try {
            result = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.PAY_ERR);
        }
        return result;
    }

    @Override
    public void saveAliPayOrder(String out_trade_no, String trade_no, String total_amount) {
        EntityWrapper<Order> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("unified_num",out_trade_no);
        List<Order> orders = orderService.selectList(entityWrapper);
        if(null == orders || orders.size()<1){
            throw new BusinessException(ErrorCode.ORDER_VERIFICATION_ERR);
        }
        BigDecimal orderTotalAmount = orders.stream().map(Order::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        if(!orderTotalAmount.toString().equals(total_amount)){
            throw new BusinessException(ErrorCode.PAY_AMOUNT_ERR);
        }
        Order order = new Order();
        order.setState(ResultEnum.ORDER_STATE_SUCCESS.getCode());
        order.setPaymentMethod(ResultEnum.ALI_PAY.getCode());
        orderService.update(order,entityWrapper);
    }
}
