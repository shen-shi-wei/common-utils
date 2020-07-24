package com.ssw.utils;

import com.ssw.constant.ConstantDefine;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class StringUtil {
	
	/**
	 * 分隔符=号
	 */
	public static String SEP_1 = "=";
	/**
	 * 分隔符==号
	 */
	public static String SEP_2 = "==";
	/**
	 * 分隔符===号
	 */
	public static String SEP_3 = "===";
	/**
	 * 分隔符=号
	 */
	public static String SEP_4 = "-";
	/**
	 * 分隔符=号
	 */
	public static String SEP_5 = "";
	
	/**
	 * 是否为空
	 * @param s
	 * @return
	 */
	public static boolean isNullOrEmpty(String s){
		return s == null || "".equals(s);
	}
	/**
	 * 转换字符串格式：ISO-8859-1 --> utf-8
	 * @param s
	 * @return
	 */
	public static String toUtf8FromIso8859 (String s) {
		try {
			return s == null ? null : new String(s.getBytes("ISO-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return s;
		}
	}
	/**
	 * 读取文件后缀名
	 * @param fileName
	 * @return
	 */
	public static String getFileExt(String fileName){
		if(fileName == null)
			return null;
		int index = fileName.lastIndexOf(".");
		return index == -1 ? null : fileName.substring(index, fileName.length());
	}
	/**
	 * 读取文件名除后缀名之外的东东
	 * @param fileName
	 * @return
	 */
	public static String getFileNameWithOutExt(String fileName){
		if(fileName == null)
			return null;
		int index = fileName.lastIndexOf(".");
		return index == -1 ? fileName : fileName.substring(0, index);
	}
	
	/**
	 * 读取文件名,如：d:\dir\a.txt  --> a.txt
	 * @param filePathName
	 * @return
	 */
	public static String getFileName(String filePathName){
		if(filePathName == null)
			return null;
		int start = filePathName.lastIndexOf(File.separator);
		if(start > -1 ){
			return filePathName.substring(start+1);  
		}
		return filePathName;
	}
	/**
	 * 是否是.rar后缀名
	 * @param fileName
	 * @return
	 */
	public static boolean isFileExt_RAR(String fileName){
		return isFileExt(fileName, ConstantDefine.FileType_Ext_RAR);
	}
	/**
	 * 是否是.zip后缀名
	 * @param fileName
	 * @return
	 */
	public static boolean isFileExt_ZIP(String fileName){
		return isFileExt(fileName, ConstantDefine.FileType_Ext_ZIP);
	}
	/**
	 * 是否是2D模型文件后缀名.f2d
	 * @param fileName
	 * @return
	 */
	public static boolean isFileExt_2D(String fileName){
		return isFileExt(fileName, ConstantDefine.ModelFileType_Ext_2D);
	}
	/**
	 * 是否是3D模型文件后缀名.svf
	 * @param fileName
	 * @return
	 */
	public static boolean isFileExt_3D(String fileName){
		return isFileExt(fileName, ConstantDefine.ModelFileType_Ext_3D);
	}
	/**
	 * 是否是fileExt后缀名
	 * @param fileName
	 * @return
	 */
	public static boolean isFileExt(String fileName, String fileExt){
		String fileExtStr = getFileExt(fileName);
		if(fileExt == null || fileExt == null)
			return false;
		return fileExt.equalsIgnoreCase(fileExtStr.toLowerCase());
	}
	
	/**
	 * 是否为true， s为1或true时返回true
	 * @param s
	 * @return
	 */
	public static boolean isTrue(String s){
		return ConstantDefine.Boolean_true_1.equals(s) || ConstantDefine.Boolean_true_true.equalsIgnoreCase(s);
	}
	public static boolean isOk(String s){
		return ConstantDefine.Boolean_ok_ok.equals(s) || ConstantDefine.Boolean_ok_200.equalsIgnoreCase(s);
	}
	
	/**
	 * 将String转化为Integer;s为需转化的值;it为当s为null时的默认值
	 * @param s
	 * @param it
	 * @return
	 */
	public static Integer StringToInteger(String s,Integer it){
		try{
			if(s!=null || "".equals(s)){
				it = Integer.valueOf(s);
			}
			return it;
		}catch (Exception e) {
			return it;
		}
		
	}
	
	/**
	 * 将String转化为Integer
	 * @param s
	 * @return
	 */
	public static Integer StringToInteger(String s){
		Integer	it = Integer.valueOf(s);
		return it;
	}
	
	/**
	 * 去除String前后的空格;s为需去除空格的值;def为当s为null时的默认值
	 * @param s
	 * @param Def
	 * @return
	 */
	public static String removeSpaces(String s,String Def){
		if(s!=null){
			Def = s.trim();
		}
		return Def;
	}
	/**
	 * 去除String前后的空格
	 * @param s
	 * @return
	 */
	public static String removeSpaces(String s){
		s = s.trim();
		return s;
	}

	/**
	 * Url编码
	 * @param s
	 * @return
	 */
	public static String urlEecode(String s){
		if(s == null)
			return s;
		try {
			return java.net.URLEncoder.encode(s, ConstantDefine.CHAR_UTF8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return s;
		}
	}
	
	/**
	 * 包装jsonp
	 * @param request
	 * @param s
	 * @return
	 */
	public static String wapperJsonpcallback(HttpServletRequest request, String s){
		String jsonpCallback = request.getParameter("jsonpCallback");
		if(StringUtil.isNullOrEmpty(jsonpCallback)){
			return s;
		}else{
			return jsonpCallback + "(" + s + ");";
		}
	}
	
	/**
	 * trim
	 * @param s
	 * @return
	 */
	public static String trim(Object s){
		if(s == null)
			return null;
		return s.toString().trim();
	}
	
	public static Boolean isNullOrEmpty(Object o){
		if(o == null)
			return true;
		return o.toString().equals("");
	}
	
	public static Boolean isNullOrEmptyOrUndefined(Object o){
		if(o == null)
			return true;
		return o.toString().equals("") || o.toString().equalsIgnoreCase("undefined");
	}
	
	public static Boolean isNullOrNullstringOrEmptyOrUndefined(Object o){
		if(o == null)
			return true;
		return o.toString().equals("") || o.toString().equalsIgnoreCase("null") || o.toString().equalsIgnoreCase("\"null\"") || o.toString().equalsIgnoreCase("undefined");
	}
	
	public static List<String> parseStringToList(String s, String sep, boolean trimItem){
		if(StringUtil.isNullOrEmpty(s))
			return null;
		String [] array = s.split(sep);
		List<String> list = new ArrayList<String>();
		for(int i = 0; i < array.length; i++){
			String one = array[i];
			if(trimItem)
				one = one.trim();
			if(!StringUtil.isNullOrEmpty(one))
				list.add(one);
		}
		return list;
	}
	
	public static String parseListToString(List<String> list, String sep){
		if(list == null || list.size() == 0)
			return null;
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < list.size(); i++){
			sb.append(list.get(i));
			if(i < list.size() - 1)
				sb.append(sep);
		}
		return sb.toString();
	}
	
	public static String toString(Object o){
		return toString(o, "");
	}
	
	public static String toString(Object o, String defaultValue){
		return o == null ? defaultValue : o.toString();
	}
	
	public static String arrayFirstItemToString(Object o, String defaultValue){
		if(o == null)
			return defaultValue;
		if(o instanceof String[]){
			String [] arr = ( String[] ) o ;
//			StringBuilder sb = new StringBuilder();
//			for(int i = 0; i < arr.length; i++){
//				sb.append(arr[i]);
//			}
//			return sb.toString();
			return arr[0];
		}else{
			return o.toString();
		}
	}
	
	/**
	 * 将浏览器接收的字符串改为UTF-8的
	 * @param str
	 * @return
	 */
	public static String encodeStr(String str) {
		return encodeStr(str, "ISO-8859-1", "UTF-8");
	}
	
	/**
	 * 将浏览器接收的字符串改为UTF-8的
	 * @param str
	 * @return
	 */
	public static String encodeStr(String str, String fromCharset, String toCharset) {
		try {
			return new String(str.getBytes(fromCharset), toCharset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * unicode 转字符串
	 */
	public static String unicode2String(String unicode) {
		if(unicode == null || unicode.indexOf("\\u") == -1)
			return unicode;
		try{
		int start = 0;
		int end = 0;
		final StringBuffer buffer = new StringBuffer();
		while (start > -1) {   
			end = unicode.indexOf("\\u", start + 2);
			String charStr = "";
			if (end == -1) {
				charStr = unicode.substring(start + 2, unicode.length());
			} else {
				charStr = unicode.substring(start + 2, end);
			}
			char letter = (char) Integer.parseInt(charStr, 16);// 16进制parse整形字符串。   
			buffer.append(new Character(letter).toString());
			start = end;
		}
		return buffer.toString(); 
		}catch(Exception e){
			e.printStackTrace();
			return unicode;
		}
//		StringBuffer string = new StringBuffer();
//		String[] hex = unicode.split("\\\\u");
//		for (int i = 1; i < hex.length; i++) {
//			// 转换出每一个代码点
//			int data = Integer.parseInt(hex[i], 16);
//			// 追加成string
//			string.append((char) data);
//		}
//		return string.toString();
	}
	
	public static String urlDecode(String s){
		if(StringUtil.isNullOrEmpty(s)){
			return s;
		}
		
		try {
			s = URLDecoder.decode(s, ConstantDefine.CHAR_UTF8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return s;
	}
	
	/**
	 * 从Map中读取值
	 * @param map
	 * @param key
	 * @return
	 */
	public static String getMapKeyValue(Map map, String key){
		return getMapKeyValue(map, key, false);
	}
	
	/**
	 * 从Map中读取值
	 * @param map
	 * @param key
	 * @param print
	 * @return
	 */
	public static String getMapKeyValue(Map map, String key, boolean print){
		String value = map == null || !map.containsKey(key) ? null : arrayFirstItemToString(map.get(key), null);
		if(print)
			System.out.println("[getMapKeyValue----]\t[key=" + key + "]\t[value=" + value + "]\t");
		return value;
	}
	
	/**
	 * 打印Map
	 * @param map
	 * @return
	 */
	public static void printMapKey(Map map){
		if(map != null && !map.isEmpty()){
			Map<String, Object> requestParameterMap = map;
			for(Map.Entry<String, Object> kv : requestParameterMap.entrySet()){
				System.out.println("[printMapKey----]\t[key=" + kv.getKey() + "]\t[value=" + arrayFirstItemToString(kv.getValue(), null) + "]\t");
			}
		}
	}
	
	/**
	 * 获取随机6位密码
	 * @return
	 */
	public static String getRandomPassword(){
		return String.valueOf((int)((Math.random()*9+1)*100000));
	}
	
	public static String getRandomThreeNum(){
		return String.valueOf((int)(Math.random()*900)+100);
	}

	/** 
	 * MD5 加密
	 */
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("utf-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}
	
	public static String defaultConversionFileSize(long fileSize){
		   float size = (float)fileSize/1024;
		   DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
		   return df.format(size);
	}
	
//	public static boolean isThirdAccoutInList(String thirdAccout){
//		return ConstantDefine.ThirdAccount_1_facebook.equalsIgnoreCase(thirdAccout) || ConstantDefine.ThirdAccount_2_twitter.equalsIgnoreCase(thirdAccout);
//	}
	
	public static String getString(Object o, String defaultValue){
		return o == null ? defaultValue : o.toString();
	}
	
	public static boolean isNumeric(String str){
		if(isNullOrEmpty(str))
			return false;
		 Pattern pattern = Pattern.compile("[0-9]*"); 
		 Matcher isNum = pattern.matcher(str);
		 if( !isNum.matches() ){
			  return false; 
		 } 
		 return true; 
		 
	}
	
	
	
	public static String getUrlPathFileName(String url){
		if(url==null)
			return null;
		url = url.replace("http://", "##");
		int beginIndex=url.indexOf("/");
		return beginIndex >= url.length() -1 ? "" : url.substring(beginIndex + 1);
	}
	
	public static String getUrlFileName(String url){
		if(url==null)
			return null;
		int beginIndex=url.lastIndexOf('/');
		return beginIndex >= url.length() -1 ? "" : url.substring(beginIndex + 1);
	}
	
	public static String getFileSuffix(String fileName){
		if(fileName==null)
			return null;
		int beginIndex=fileName.lastIndexOf('.');
		return fileName.substring(beginIndex);
	}
	

	/**
	 * 检测是否存在录像
	 * @param fileName
	 * @param files
	 * @return
	 */
	public static boolean containsFileNameInList(String fileName, FTPFile[] files){
		if(StringUtil.isNullOrEmpty(fileName) || files == null || files.length == 0)
			return false;
		for(int i = 0; i < files.length; i++){
			if(fileName.equalsIgnoreCase(files[i].getName()))
				return true;
		}
		return false;
	}
	/** 
	 * 比较版本号的大小,前者大则返回一个正数,后者大返回一个负数,相等则返回0 
	 * @param version1 
	 * @param version2 
	 * @return 
	 */  
	public static int compareVersion(String version1, String version2) {
		if (version1 == null || version2 == null) {
			return 0;
		}  
		String[] versionArray1 = version1.split("\\.");//注意此处为正则匹配，不能用"."； 
		String[] versionArray2 = version2.split("\\.");  
		int idx = 0;
		int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值 
		int diff = 0;
		while (idx < minLength
				&& (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度  
				&& (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符  
			++idx;
		}  
		//如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；  
		diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
		return diff;
	}
	
	/** 
	 * byte(字节)根据长度转成kb(千字节)和mb(兆字节) 
	 *  
	 * @param bytes 
	 * @return 
	 */  
	public static String bytes2KbMb(Long bytes) {
		if(bytes == null)
			return "";
		BigDecimal filesize = new BigDecimal(bytes);
		BigDecimal megabyte = new BigDecimal(1024 * 1024);
		float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)
				.floatValue();
		if (returnValue > 1)
			return (returnValue + "MB");
		BigDecimal kilobyte = new BigDecimal(1024);
		returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP)
				.floatValue();
		return (returnValue + "KB");
	}
	

	public static String generateRandomNickName(int low,int max){
		 String chars = "abcdefghijklmnopqrstuvwxyz";
		 StringBuffer nickName=new StringBuffer();
		 Random random=new Random();
		 int len=random.nextInt(max-low+1)+low;
		 for(int i=0;i<len;i++){
			 char ch=chars.charAt((int)(Math.random() * 26));
			 if(i==0){
				 nickName.append(String.valueOf(ch).toUpperCase());
				 continue;
			 }
			 nickName.append(ch);
		 }
		 return nickName.toString();
	}
	
	public static String generateRandomNumber(int low,int max){
		 String chars = "0123456789";
		 StringBuffer nickName=new StringBuffer();
		 Random random=new Random();
		 int len=random.nextInt(max-low+1)+low;
		 for(int i=0;i<len;i++){
			 char ch=chars.charAt((int)(Math.random() * 10));
			 if(i==0){
				 nickName.append(String.valueOf(ch).toUpperCase());
				 continue;
			 }
			 nickName.append(ch);
		 }
		 return nickName.toString();
	}
	/**
	 * 首字母转大写
	 * @param name
	 * @return
	 */
	public static String captureName(String name) {
		 char[] cs=name.toCharArray();
		 if(Character.isLowerCase(cs[0]))
			cs[0]-=32;
		 return String.valueOf(cs);
	 }
	
	
	/**
	 * 格式化输出jsonp回调字符串
	 * @param json
	 * @return
	 */
	public static String toJsonpCallbackString(String json){
		return ConstantDefine.JsonpCallback + json + ConstantDefine.END_1;
	}
	/**
	 * Jsonp文件名前缀
	 * @param fileName
	 * @return
	 */
	public static String toJsonpCallbackFileName(String fileName){
		return ConstantDefine.JsonpFileNamePre + fileName;
	}
	
	/**
	 * 使用JsonpCallback包装
	 * @param jsonpCallback
	 * @param jsonStr
	 * @return
	 */
	public static String wapperJsonpCallback(String jsonpCallback, String jsonStr){
		if(!StringUtil.isNullOrEmpty(jsonpCallback)){
			StringBuilder sb = new StringBuilder();
			sb.append(jsonpCallback);
			sb.append(ConstantDefine.SEP_7);
			sb.append(jsonStr);
			sb.append(ConstantDefine.END_1);
			return sb.toString();
		}else{
			return jsonStr;
		}
	}
	/**
	 * 编码id
	 * @param id
	 * @return
	 */
//	public static String encodeId(Long id){
//		return Base64.encodeN(id == null ? null : id.toString(), false);
//	}
	/**
	 * 格式化Base64的加密字符串，即保证字符串的长度是4的倍数，如果不是则用=号补足
	 * @param s
	 * @return
	 */
	public static String formatBase64EncodedStr(String s){
		if(isNullOrEmpty(s))
			return s;
		int ilen = s.length() % 4;
		if(ilen != 0){
			switch(ilen){
				case 1 : s += SEP_3;break;
				case 2 : s += SEP_2;break;
				case 3 : s += SEP_1;break;
			}
		}
		return s;
	}
	
	/**
	 * 获取UUID，并除去其中的"-".
	 * 
	 * @return
	 */
	public static String getUUID() {
		StringBuffer sbrUuid = new StringBuffer();
		String uuid = UUID.randomUUID().toString();// 获取随机唯一标识符
		// 去掉标识符中的"-"
//		sbrUuid.append(uuid.substring(0, 8)).append(uuid.substring(9, 13))
//				.append(uuid.substring(14, 18)).append(uuid.substring(19, 23))
//				.append(uuid.substring(24));
//		return sbrUuid.toString();
		return uuid.replace(SEP_4, SEP_5);
	}

	/**
	 * 字符串压缩
	 * @param paramString
	 * @return
	 */
	public static final byte[] compress(String paramString) {	
		if (paramString == null)	
			return null;	
		ByteArrayOutputStream byteArrayOutputStream = null;	
		ZipOutputStream zipOutputStream = null;	
		byte[] arrayOfByte;	
		try {	
			byteArrayOutputStream = new ByteArrayOutputStream();	
			zipOutputStream = new ZipOutputStream(byteArrayOutputStream);	
			zipOutputStream.putNextEntry(new ZipEntry("0"));	
			zipOutputStream.write(paramString.getBytes());	
			zipOutputStream.closeEntry();	
			arrayOfByte = byteArrayOutputStream.toByteArray();	
		} catch (IOException localIOException5) {	
			arrayOfByte = null;	
		} finally {	
			if (zipOutputStream != null)	
				try {	
					zipOutputStream.close();	
				} catch (IOException localIOException6) {	
			}	
			if (byteArrayOutputStream != null)	
				try {	
					byteArrayOutputStream.close();	
				} catch (IOException localIOException7) {	
			}	
		}	
		return arrayOfByte;	
	}	
	
	/**
	 * 字符串解压缩
	 * @param paramArrayOfByte
	 * @return
	 */
	@SuppressWarnings("unused")	
	public static final String decompress(byte[] paramArrayOfByte) {
		if (paramArrayOfByte == null)
			return null;
		ByteArrayOutputStream byteArrayOutputStream = null;
		ByteArrayInputStream byteArrayInputStream = null;
		ZipInputStream zipInputStream = null;
		String str;	
		try {	
			byteArrayOutputStream = new ByteArrayOutputStream();
			byteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);
			zipInputStream = new ZipInputStream(byteArrayInputStream);
			ZipEntry localZipEntry = zipInputStream.getNextEntry();
			byte[] arrayOfByte = new byte[1024];
			int i = -1;
			while ((i = zipInputStream.read(arrayOfByte)) != -1)
				byteArrayOutputStream.write(arrayOfByte, 0, i);
			str = byteArrayOutputStream.toString();
		} catch (IOException localIOException7) {
			str = null;
		} finally {
			if (zipInputStream != null)
				try {
					zipInputStream.close();
				} catch (IOException localIOException8) {
				}	
			if (byteArrayInputStream != null)
				try {	
					byteArrayInputStream.close();
				} catch (IOException localIOException9) {
				}	
			if (byteArrayOutputStream != null)
				try {	
					byteArrayOutputStream.close();
				} catch (IOException localIOException10) {
			}
		}
		return str;
	}	
	/**
	 * param value需要切分的字符串  word1切分标志1 word2切分标志2
	 * */
	public static Map<String,String> segmentationString(String value,String word1,String word2) {
		System.out.println(value+"----------------------------------------");
		try {
			value = URLDecoder.decode(value,"utf-8");
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String,String> map = new HashMap<String,String>();
		if(!"".equals(value)&&!"".equals(word1)) {
			String[] values = value.split(word1);
			for(int i = 0,leni = values.length; i<leni; i++) {
				String mapkey = values[i].split(word2)[0];
				String mapvalue = values[i].split(word2)[1];
				map.put(mapkey, mapvalue);
			}
		} 
		
		return map;
	}
	public static String sqlIn(String value,String reg){
		String result = "";
		if(!StringUtil.isNullOrEmpty(value) && !StringUtil.isNullOrEmpty(reg)){
			result = "('"+value.replaceAll(reg,"','")+"')";
		} else {
			result =  "('')";
		}
		return result;
	}
	
	public static String formatModelUrl(String prefix, String halfPath){
		return StringUtil.formatModelUrlPath(prefix+StringUtil.formatModelUrlPath(halfPath,false),true);
	}
	public static String formatModelUrlPath(String source,boolean ifFullPath){
		if(!StringUtils.isEmpty(source)){
			source = source.replace("\\", "/");//清理反斜杠"\"
			//有更好的方式？正则处理叠词，后续优化实现
			while(source.contains("//")){
				source = source.replace("//", "/");//清理多层斜杠"//"
			}
			//兼容老数据
			if(!ifFullPath){
				if(!source.startsWith("/")){
					source = "/"+source;
				}
			}else{
				source = source.replaceFirst("/", "//");
			}
		}
		return source;
	}

	public static boolean checkNumber(String value){
		String IntegerRegx = "^(-?[1-9]\\d*)|0$";
		if ( value.matches(IntegerRegx)) {
			return true;
		}
		return false;
	}
	
}
