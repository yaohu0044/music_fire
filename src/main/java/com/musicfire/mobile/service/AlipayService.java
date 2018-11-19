package com.musicfire.mobile.service;

import com.musicfire.modular.order.entity.Order;
import com.musicfire.modular.system.entity.AlipayUserInfo;


public interface AlipayService {

	
	String tradePay(Order order);

	int saveAlipayUser(AlipayUserInfo alipayUserInfo);


	//List<AlipayUserInfo> queryAlipayUserList(QueryCondition qc, PageInfo pageInfo);
	
}
