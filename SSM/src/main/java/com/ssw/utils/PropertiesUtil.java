package com.ssw.utils;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

public class PropertiesUtil {
	private static final byte[] KEY = { 9, -1, 0, 5, 39, 8, 6, 19 };
	private static Map<String, String> ctxPropertiesMap;
	private List<String> decryptProperties;
	
	public static Map<String,String> getProperties(String filepath) {
		Map<String,String> mapResult = new HashMap<String,String>();
		Properties prop = new Properties();
		InputStream file = null;
		try {
			file = new BufferedInputStream (new FileInputStream(filepath));
			prop.load(file);
			Iterator<String> it=prop.stringPropertyNames().iterator();
			while(it.hasNext()) {
				String key = it.next();
				mapResult.put(key, prop.getProperty(key));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return mapResult;
	}

	/**
	 *            the decryptPropertiesMap to set
	 */
	public void setDecryptProperties(List<String> decryptProperties) {
		this.decryptProperties = decryptProperties;
	}

	/**
	 * Get a value based on key , if key does not exist , null is returned
	 * 
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		try {
			return ctxPropertiesMap.get(key);
		} catch (MissingResourceException e) {
			return null;
		}
	}

	/**
	 * Get a value based on key , if key does not exist , null is returned
	 * 
	 * @param key
	 * @return
	 */
	/*public static String getString(String key, String defaultValue) {
		try {
			String value = ctxPropertiesMap.get(key);
		//	if (DataUtil.isEmpty(value)) {
		//	}
			//return value;
		} catch (MissingResourceException e) {
			return defaultValue;
		}
	}*/

	/**
	 * 根据key获取值
	 * 
	 * @param key
	 * @return
	 */
	public static int getInt(String key) {
		return Integer.parseInt(ctxPropertiesMap.get(key));
	}

	/**
	 * 根据key获取值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getInt(String key, int defaultValue) {
		String value = ctxPropertiesMap.get(key);
		if (StringUtils.isBlank(value)) {
			return defaultValue;
		}
		return Integer.parseInt(value);
	}

	/**
	 * 根据key获取值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBoolean(String key, boolean defaultValue) {
		String value = ctxPropertiesMap.get(key);
		if (StringUtils.isBlank(value)) {
			return defaultValue;
		}
		return new Boolean(value);
	}

	public static <T> T copyRequestParamToBean(HttpServletRequest request, Class<T> beanType){
		return copyRequestParamToBean(request,beanType,null);
	}

	public static <T> T copyRequestParamToBean(HttpServletRequest request, Class<T> beanType, String paramNames)
	{
		// 1. 声明参数对象
		//可以将此对象换为HashMap
		JSONObject params = new JSONObject();
		// 2. 获取所有的请求参数
		Enumeration<String> parameters = request.getParameterNames();

		if(!StringUtils.isBlank(paramNames)){
			String[] paramNameArray = paramNames.split(",");
			for (String currParam : paramNameArray){
				if(StringUtils.isBlank(request.getParameter(currParam))){
					return null;
				}
			}
		}

		// 3. 将所有的参数保存到json对象中
		while (parameters.hasMoreElements())
		{
			// 参数名称
			String paramname = parameters.nextElement();
			if(StringUtils.isBlank(request.getParameter(paramname))){
				//System.out.println("-----参数-----"+paramname+"-----不能为空");
				return null;
			}
			params.put(paramname, request.getParameter(paramname));
		}
		T object = null;
		try {
			// 4.遍历匹配java查询Bean属性
			object = beanType.newInstance();
			Field[] fields = object.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++)
			{
				// 4.1获取属性信息
				// 属性信息
				Field field = fields[i];
				// javaBean属性名称
				String attributename = field.getName();
				// 字段数据类型
				String type = field.getGenericType().toString();
				if (!params.containsKey(attributename))
				{
					continue;// 只更新上送的参数
				}
				if (params.get(attributename) == null)
				{
					continue;// 过滤掉空参数
				}
				// 将属性的首字符大写，方便构造get，set方法
				String setName = "set" + attributename.substring(0, 1).toUpperCase() + attributename.substring(1);
				// 4.2设置属性参数
				switch (type)
				{
					case "class java.math.BigDecimal":
					{
						// 获取set方法
						Method method = object.getClass().getMethod(setName, new Class[] { BigDecimal.class });
						// 执行set方法
						method.invoke(object, new Object[] { new BigDecimal(params.getString(attributename)) });
						break;
					}
					case "class java.lang.Boolean":
					{
						// 获取set方法
						Method method = object.getClass().getMethod(setName, new Class[] { Boolean.class });
						// 执行set方法
						method.invoke(object, new Object[] { new Boolean(params.getString(attributename)) });
						break;
					}
					case "class java.lang.Byte":
					{
						// 获取set方法
						Method method = object.getClass().getMethod(setName, new Class[] { Byte.class });
						// 执行set方法
						method.invoke(object, new Object[] { new Byte(params.getString(attributename)) });
						break;
					}
					case "class java.util.Date":
					{
						// 获取set方法
						Method method = object.getClass().getMethod(setName, new Class[] { Date.class });
						Date date = new Date(params.getLong(attributename));
						// 执行set方法
						method.invoke(object, new Object[] { date });
						break;
					}
					case "class java.lang.Double":
					{
						// 获取set方法
						Method method = object.getClass().getMethod(setName, new Class[] { Double.class });
						// 执行set方法
						method.invoke(object, new Object[] { new Double(params.getString(attributename)) });
						break;
					}
					case "class java.lang.Float":
					{
						// 获取set方法
						Method method = object.getClass().getMethod(setName, new Class[] { Float.class });
						// 执行set方法
						method.invoke(object, new Object[] { new Float(params.getString(attributename)) });
						break;
					}
					case "class java.lang.Integer":
					{
						// 获取set方法
						Method method = object.getClass().getMethod(setName, new Class[] { Integer.class });
						// 执行set方法
						method.invoke(object, new Object[] { params.getInt(attributename) });
						break;
					}
					case "class java.lang.Long":
					{
						// 获取set方法
						Method method = object.getClass().getMethod(setName, new Class[] { Long.class });
						// 执行set方法
						method.invoke(object, new Object[] { params.getLong(attributename) });
						break;
					}
					case "class java.lang.String":
					{
						// 获取set方法
						Method method = object.getClass().getMethod(setName, new Class[] { String.class });
						// 执行set方法
						method.invoke(object, new Object[] { params.getString(attributename) });
						break;
					}
					case "class net.sf.json.JSONObject":
					{
						// 获取set方法
						Method method = object.getClass().getMethod(setName, new Class[] { JSONObject.class });
						JSONObject json = new JSONObject();
						// 执行set方法
						method.invoke(object, new Object[] { json.fromObject(params.getString(attributename)) });
						break;
					}
					case "class java.util.List":
					{
						break;
					}
					case "class java.util.ArrayList":
					{
						break;
					}
					default:
					{
						throw new Exception("未设定的数据类型：" + type);
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return object;
	}
	public static boolean paramIsNull(Object... objects){
		for (Object object : objects){
			if(object==null||StringUtils.isBlank(object.toString())){
				return true;
			}
		}
		return false;
	}

	public static boolean paramIsAllNull(Object... objects){
		for (Object object : objects){
			if(object==null||StringUtils.isBlank(object.toString())){
				return true;
			}
		}
		return false;
	}
}
