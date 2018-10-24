package com.musicfire.common.utiles;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


public class ObjUtil {

    /**
     * 带入一个对象，一个对象中的属性名称，取得该属性的值
     * @param obj
     * @param strProperty
     * @return
     * @throws Exception
     */
    public static String getPropertyValueFormObject(Object obj,
            String strProperty) throws Exception  {
        //zzz:if (!StringUtils.isValidateString(strProperty)) {
        //	return "";
        //}
        if (obj == null) {
            return "";
        }
        Class clazz = obj.getClass();
        HashMap map = new HashMap();
    getClass(clazz,map,obj);
        HashMap newMap = convertHashMap(map);

        if (newMap == null) {
            return "";
        } else {
            //zzz:Object objReturn = newMap.get(StringUtils.validateString(strProperty));
            Object objReturn = newMap.get(strProperty);
            if (objReturn==null) {
                return "";
            } else {
                return objReturn.toString();
            }

        }

    }
    /**
     *
    */
    private static void getClass(Class clazz,HashMap map,Object obj) throws Exception{
        if(clazz.getSimpleName().equals("Object")){
            return;
        }

    Field[] fields = clazz.getDeclaredFields();
        if (fields == null || fields.length <= 0) {
            throw new Exception("当前对象中没有任何属性值");
        }
        for(int i=0;i<fields.length;i++){
            fields[i].setAccessible(true);
            String name=fields[i].getName();
            Object value=fields[i].get(obj);
            map.put(name,value);

        }
        Class superClzz=clazz.getSuperclass();
        getClass(superClzz,map,obj);
    }
    /**
     *
     * @param map
     * @return
     * @throws Exception
     */
    private static HashMap convertHashMap(HashMap map) throws Exception {

        HashMap newMap = new HashMap();
        Set keys = map.keySet();
        Iterator it = keys.iterator();
        while (it.hasNext()) {
            Object key = it.next();
            convertToString(map.get(key), newMap, key);
        }

        return newMap;
    }

    /**
     *
     * @param value
     * @param newMap
     * @param key
     */
    private static void convertToString(Object value, HashMap newMap, Object key) {
        if (value != null) {
            Class clazz = value.getClass();
            if (isBaseType(clazz)) {
                newMap.put(key, value.toString());
            } else if (clazz == String.class) {
                newMap.put(key, value.toString());
            } else if (clazz == Date.class) {
                Date date = (Date) value;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                newMap.put(key, sdf.format(date));
            } else if (clazz == Timestamp.class) {
                Timestamp timestamp = (Timestamp) value;
                long times = timestamp.getTime();
                Date date = new Date(times);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                newMap.put(key, sdf.format(date));
            } else if (clazz == java.sql.Date.class) {
                java.sql.Date sqlDate = (java.sql.Date) value;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                newMap.put(key, sdf.format(sqlDate));
            } else {
                newMap.put(key, value);
            }
        } else {
            newMap.put(key, value);
        }

    }


    /**
     *
     * @param clazz
     * @return
     */
    private static boolean isBaseType(Class clazz) {

        if (clazz == Integer.class) {
            return true;
        }
        if (clazz == Long.class) {
            return true;
        }
        if (clazz == Double.class) {
            return true;
        }
        if (clazz == Byte.class) {
            return true;
        }
        if (clazz == Float.class) {
            return true;
        }
        if (clazz == Short.class) {
            return true;
        }
        if (clazz == Boolean.class) {
            return true;
        }
        return false;
    }

    public static  <T> T ifNull(T parm,T out)
    {
        return null==parm ? out:parm;
    }

    public static <T> boolean ifNull(T parm)
    {
        return null==parm;
    }

    public static String ifBlank(String parm,String out)
    {
        return StringUtils.isBlank(parm)?out:parm;
    }

    public static boolean isNumeric(String str)
    {
        if(StringUtils.isBlank(str))
            return false;

        for (int i = str.length();--i>=0;)
        {
           if (!Character.isDigit(str.charAt(i)))
            return false;
        }

        return true;
    }

    /**
     * 参数工具
     * @Title paramsUtil
     * @Description 把参数转换成string 避免为 null的情况
     * @author tianzy
     * @param params 参数
     * @return
     */
    public static String paramsUtil(Object params){
        if(org.springframework.util.StringUtils.isEmpty(params))
            return "";
        return params.toString();
    }
}
