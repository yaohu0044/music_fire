package com.musicfire.mobile.service;

import com.baomidou.mybatisplus.service.IService;
import com.musicfire.mobile.entity.AliPayUserInfo;


public interface AliPayService extends IService<AliPayUserInfo> {

	int saveAliPayUser(AliPayUserInfo alipayUserInfo);

	/**
	 * 保存 更新订单信息
	 * @param out_trade_no 统一支付号
	 * @param trade_no 交易流水号
	 * @param total_amount 交易金额
	 */
    void saveAliPayOrder(String out_trade_no, String trade_no, String total_amount);
}
