package com.musicfire.mobile.service;

import com.baomidou.mybatisplus.service.IService;
import com.musicfire.mobile.entity.AliPayUserInfo;

public interface AliPayService extends IService<AliPayUserInfo> {
	  int saveAliPayUser(AliPayUserInfo paramAliPayUserInfo);

	  void saveAliPayOrder(String paramString1, String paramString2, String paramString3);
}
