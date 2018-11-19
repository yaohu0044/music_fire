package com.musicfire.mobile.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.musicfire.common.config.AliPayConfig;
import com.musicfire.common.config.redisdao.RedisConstant;
import com.musicfire.common.config.redisdao.RedisDao;
import com.musicfire.mobile.service.AlipayService;
import com.musicfire.mobile.wxpay.WXPayUtil;
import com.musicfire.modular.order.entity.Order;
import com.musicfire.modular.system.entity.AlipayUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DecimalFormat;

@Service("alipayService")
@Slf4j
public class AlipayServiceImpl  implements AlipayService {

	@Resource
	private AliPayConfig aliPayConfig;

	@Resource
	private RedisDao redisDao;


    @Transactional
	public int saveAlipayUser(AlipayUserInfo AlipayUserInfo) {
		int ret =0;

		if(redisDao.exist(RedisConstant.ALIPAY_PRE+AlipayUserInfo.getUserId())){
			redisDao.update(RedisConstant.ALIPAY_PRE+AlipayUserInfo.getUserId(),AlipayUserInfo);
		}else {
			//ret = baseMapper.insert(AlipayUserInfo);
            redisDao.add(RedisConstant.ALIPAY_PRE+AlipayUserInfo.getUserId(),AlipayUserInfo);
		}

		return  ret;
	}
	
	public String tradePay(Order order){
		
		String result = null;

		// 超时时间 可空
		String timeout_express="2m";
		// 销售产品码 必填
		String product_code="QUICK_WAP_WAY";
		
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(aliPayConfig.getOpen_api_domain(),
				aliPayConfig.getAppid(),
				aliPayConfig.getPrivate_key(),
				"json",
				"utf-8",
				aliPayConfig.getAlipay_public_key(),
				aliPayConfig.getSign_type());

		AlipayTradeWapPayRequest alipay_request=new AlipayTradeWapPayRequest();

		// 封装请求支付信息
		AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
		model.setOutTradeNo( WXPayUtil.createOrderNo());
		model.setSubject("");
		DecimalFormat df1 = new DecimalFormat("0.00");
		String amount = df1.format(2);

		model.setTotalAmount(amount);
		model.setBody("");
		model.setTimeoutExpress(timeout_express);
		model.setProductCode(product_code);
		alipay_request.setBizModel(model);
		// 设置异步通知地址
		alipay_request.setNotifyUrl(aliPayConfig.getNotify_url());
		// 设置同步地址
		alipay_request.setReturnUrl(aliPayConfig.getReturn_url());
		try {
			// 调用SDK生成表单
			result = alipayClient.pageExecute(alipay_request).getBody();
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
    	return result;
	}
}
