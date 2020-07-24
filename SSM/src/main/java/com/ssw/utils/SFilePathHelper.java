package com.ssw.utils;

import java.util.Map;

public class SFilePathHelper {
	
	private static Map<String, String> dataMap;
	private static final String propertyFilename = "common-filepath.properties";

	static{
		String filepath = Thread.currentThread().getContextClassLoader().getResource("").getPath() +propertyFilename;
		dataMap = PropertiesUtil.getProperties(filepath);
	}
	
	private static void createPropertiesMap(){
		if(dataMap==null || dataMap.isEmpty()){
			String filepath = Thread.currentThread().getContextClassLoader().getResource("").getPath() +propertyFilename;
			dataMap = PropertiesUtil.getProperties(filepath);
		}
	}
	
	/** 测试 **/
	public static String demo() {
		createPropertiesMap();
		return dataMap.get("demo");
	}

	/** 这里的password(秘钥必须是16位的) **/
	public static String getTokenKeyBytes() {
		createPropertiesMap();
		return dataMap.get("AES.token.keyBytes");
	}
	public static String getAESTokenAlgorithmStr() {
		createPropertiesMap();
		return dataMap.get("AES.token.algorithmStr");
	}

}
