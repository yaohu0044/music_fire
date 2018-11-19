package com.musicfire.mobile.entity;

import lombok.Data;

import java.util.Date;

/**
 * 微信登录实体类
 * 
 * @author xiangze
 *
 */
@Data
public class WechatAuth {
	// 主键ID
	private Long wechatAuthId;
	// 微信获取用户信息的凭证，对于某个公众号具有唯一性
	private String openId;
	// 创建时间
	private Date createTime;

	private String userId;
//	// 用户信息
//	private SysUser userInfo;

}
