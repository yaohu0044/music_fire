package com.musicfire.common.utiles;

public class Conf
{
	public static ConfIns confIns = new ConfIns();
	public static String getValue(String key) {
		return confIns.getValue(key);
	}
	
	public static String getValue(String key,String conf) {
		return confIns.getValue(key,conf);
	}
	
}
