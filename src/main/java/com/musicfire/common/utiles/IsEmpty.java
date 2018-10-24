package com.musicfire.common.utiles;

import java.util.List;
import java.util.Map;

//是否为空
public class IsEmpty {
	
		/**
		 * 集合中是否包含参数key
		 * @param params
		 * @param key
		 * @return
		 * @throws Exception
		 */
		public static boolean Is(Map<String,Object> params,String key) throws Exception{
			if(params==null)  return false;
			if(params.get(key)==null) return false;
			if("".equals(params.get(key).toString())) return false;
			if("null".equals(params.get(key).toString())) return false;
			return true;
		}

		/**
		 * 多参数验证
		 * @param params
		 * @param key
		 * @return
		 * @throws Exception
		 */
		public static boolean Is(Map<String,Object> params,String ... key) throws Exception{
			for(String k:key){
				if(!Is(params,k)){
					return false;
				}
			}
			return true;
		}

		/**
		 * 机构学校是否为空
		 * @param params
		 * @param key
		 * @return
		 * @throws Exception
		 */
		public static boolean IsOS(Map<String,Object> params,String key) throws Exception{
			if(params==null)  return false;
			if(params.get(key)==null) return false;
			String val=params.get(key).toString().trim();
			if("".equals(val)) return false;
			if("-1".equals(val)) return false;
			if("0".equals(val)) return false;
			return true;
		}
		/**
		 * 集合中是否包含这些多个参数
		 * @param params
		 * @param keys
		 * @throws Exception
		 */
		public static void IsOk(Map<String,Object> params,String ... keys) throws Exception{
			for(String key:keys){
				if(!Is(params,key)){
					throw new  Exception("参数"+ key+ "为空");
				}
			}
		}
		
		/**
		 * 判断字符串是否为空
		 * @param str
		 * @return
		 * @throws Exception
		 */
		public static boolean Is(String str){
			if("null".equals(str)) return false;
			if(str==null) return false;
			if("".equals(str.trim())) return false;
			return true;
		}
		/**
		 * 判断字符串是否为空
		 * @param str
		 * @return
		 * @throws Exception
		 */
		public static boolean Is(Object str){
			if("null".equals(str)) return false;
			if(str==null) return false;
			if("".equals(str.toString().trim())) return false;
			return true;
		}
		
		/**
		 * 设置一个默认值
		 * @param params 集合
		 * @param key key
		 * @param def 默认值
		 * @return
		 * @throws Exception
		 */
		public static String get(Map<String,Object> params,String key,String def) throws Exception{
			if(Is(params,key)){
				return params.get(key).toString();
			}else{
				return def;
			}
		}
		/**
		 * 判断字符组中是否包含这些值中的一个或多个
		 * @param str
		 * @return
		 * @throws Exception
		 */
		public static boolean CheckValOr(String str,String ... vals) throws Exception{
			if(str==null) return false;
			String [] arr=str.split(",");
			for(String a:arr){
				for(String v:vals){
					if(v.equals(a)) return true;
				}
			}
			return false;
		}
		/**
		 * 判断字符组中是否包含这些所有值
		 * @param str
		 * @return
		 * @throws Exception
		 */
		public static boolean CheckValAnd(String str,String ... vals) throws Exception{
			if(str==null) return false;
			String [] arr=str.split(",");
			boolean flag=false;
			for(String v:vals){
				for(String a:arr){
					if(a.equals(v)){
						flag=true;
						break;
					}
				}
				if(flag){
					flag=false;
					continue;
				}else{
					return false;
				}
			}
			return true;
		}
		/**
		 * 去除尾部的
		 * @param str
		 * @return
		 * @throws Exception
		 */
		public static String Delend(String str) throws Exception{
			str=str.trim();
			if(str.endsWith(","));
			return str.substring(0,str.length()-1);
		}
		
		/**
		 * 判断字符串是否包含某一元素
		 * @param str
		 * @param yuansu
		 * @return
		 * @throws Exception
		 */
		public static boolean check(String str,String yuansu,String split) throws Exception{
			if(Is(str) && Is(yuansu)){
				if(split==null)split=",";
				String[] strs = str.split(split);
				for(String s:strs){
					if(s.trim().equals(yuansu)) return true;
				}
				return false;
			}else{
				return false;
			}
		}
		/**
		 * 集合转字符串
		 * @param list
		 * @return
		 * @throws Exception
		 */
		public static String toString(List<String> list) throws Exception{
			String str="";
			for(int i=0;i<list.size();i++){
				if(i!=0)str+=",";
				str+=list.get(i);
			}
			return str;
		}
}
