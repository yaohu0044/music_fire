package com.musicfire.common.utiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 
 * @author mr li
 *JSON格式转换器
 */
public class JsonTool {
	private static ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 * 
	 * 将实体类型转换为map格式
	 */
	@SuppressWarnings("rawtypes")
	public static Map Obj2Map(Object obj) throws Exception{
		String json = mapper.writeValueAsString(obj);
		return mapper.readValue(json.toString(),Map.class) ;
	}
	/**
	 * 将实体对象转换为实体对象
	 * @param obj	被转换的
	 * @param t		转换后的
	 * @return
	 * @throws Exception
	 */
	public static <T>T Obj2T(Object obj,Class<T> t) throws Exception{
		String json = mapper.writeValueAsString(obj);
		return (T) mapper.readValue(json.toString(),t) ;
	}

	/**
	 * 数组转换为指定类型的数组
	 * @param obj
	 * @param t
	 * @param <T>
	 * @return
	 */
	public static <T>List<T> Obj2TList (Object obj,Class<T> t){
		if(obj instanceof  List){
			List list = (List)obj;
			List<T> castList = new ArrayList<>();
			if(obj==null){
				return null;
			}
			for(int i=0;i<list.size();i++){
				try {
					String json = mapper.writeValueAsString(list.get(i));
					castList.add(mapper.readValue(json.toString(), t));
				}catch(Exception e){
					e.printStackTrace();
					return null;
				}
			}
			return castList;
		}else {
			System.err.println("对象不是一个数组");
			return null;
		}
	}
	/**
	 * 將json轉化為對象
	 * @param json
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public static <T>T Json2T(String json,Class<T> t) throws Exception{
		return (T) mapper.readValue(json,t) ;
	}
	
	/**
	 * 按json格式打印当前对象
	 * @param obj
	 * @throws Exception
	 */
	public static void print(Object obj,String ... caption) throws Exception{
		if(caption!=null && caption.length>0){
			for(String s:caption){
				System.out.print(s);
			}
		}
		System.out.println(mapper.writeValueAsString(obj));
	}
	
	
	/**
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 * 
	 * 将实体转换为json格式字符串
	 */
	public static String ToJson(Object obj) throws Exception{
		return mapper.writeValueAsString(obj);
	}
	
	@SuppressWarnings("rawtypes")
	/**
	 * 将json格式字符串转换为map
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public static Map ToMap(String json) throws Exception{
		return mapper.readValue(json,Map.class) ;
	}	
	@SuppressWarnings("unchecked")
	/**
	 * 将json格式字符串转换为泛型map
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> ToMapT(String json) throws Exception{
		return mapper.readValue(json,Map.class) ;
	}	
	/**
	 * 将json数据转换为实体对象
	 * @param json
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public static <T>T ToMapT(String json,Class<T> t) throws Exception{
		return (T) mapper.readValue(json,t) ;
	}	
	
	@SuppressWarnings("unchecked")
	public static List<Map<String,Object>> ToList(String json) throws Exception{
			@SuppressWarnings("rawtypes")
			List list= mapper.readValue(json,List.class) ;
			List<Map<String,Object>> arrlist= new ArrayList<Map<String,Object>>();
			for(Object obj:list){
				arrlist.add((Map<String,Object>)obj);
			}
			return arrlist;
	} 
	@SuppressWarnings("unchecked")
	public static List<Map<String,String>> ToList2(String json) throws Exception{
			@SuppressWarnings("rawtypes")
			List list= mapper.readValue(json,List.class) ;
			List<Map<String,String>> arrlist= new ArrayList<Map<String,String>>();
			for(Object obj:list){
				Map<String,Object> map =( Map<String,Object>)obj;
				Map<String,String> map2 =new HashMap<String,String>();
				for(String key:map.keySet()){
					map2.put(key,String.valueOf( map.get(key)));
				}
				arrlist.add(map2);
			}
			return arrlist;
	} 
		
}