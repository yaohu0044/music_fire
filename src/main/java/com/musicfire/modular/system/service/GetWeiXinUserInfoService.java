package com.musicfire.modular.system.service;

import com.musicfire.mobile.entity.WechatMpUser;

import java.util.Map;

public interface GetWeiXinUserInfoService {
	/**
	 * 获取微信用户的code
	 * @return
	 */
	String getCode(String wechatpubNo);
	/**
	 * 通过code换取网页授权access_token和openid
	 * @return
	 */
	Map<String,String> getAccessToken(String wechatpubNo, String code);
	/**
	 * 通过access_token,openid获取微信用户信息
	 * @return
	 */
	Map<String,String> getWxUserInfo(String wechatpubNo, String access_token, String openid);

	int update(String openId);

	WechatMpUser getWechatMpUserByOpenId(String openid);
}
