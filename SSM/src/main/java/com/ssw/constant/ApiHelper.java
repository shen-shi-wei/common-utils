package com.ssw.constant;
/**
 * API接口辅助类
 * @author wan
 *
 */
public class ApiHelper {
	/**是否开启Api访问, true表示调用远程Api接口， false表示调用数据库连接**/
	public static boolean API_ENABLE = false;

	/**true不进行权限认证**/
	public static boolean SECURITY_ENABLE = false;
	
	/**api接口服务端URL**/
	public static String Document_SERVER_URL = "http://lmv.bdip.17bim.com:8091/4DAnalog/remote/dochessian";
	public static String Flow_SERVER_URL = "http://lmv.bdip.17bim.com:8091/4DAnalog/remote/flowhessian";
	public static String Team_SERVER_URL = "http://lmv.bdip.17bim.com:8091/4DAnalog/remote/teamhessian";
	public static String ViewerSave_SERVER_URL = "http://lmv.bdip.17bim.com:8091/4DAnalog/remote/ViewerSaveteamhessian";
	public static String Comp_SERVER_URL = "http://lmv.bdip.17bim.com:8091/4DAnalog/remote/comphessian";
	public static String Compon_SERVER_URL = "http://lmv.bdip.17bim.com:8091/4DAnalog/remote/componhessian";
	public static String Cowork_SERVER_URL="http://lmv.bdip.17bim.com:8091/4DAnalog/remote/Coworkhessian";
	public static String UpdateBind_SERVER_URL = "http://lmv.bdip.17bim.com:8091/4DAnalog/remote/upBindhessian";
	public static String MsMessage_SERVER_URL="http://lmv.bdip.17bim.com:8091/4DAnalog/remote/MsMessagehessian";
	public static String MsRoom_SERVER_URL="http://lmv.bdip.17bim.com:8091/4DAnalog/remote/MsRoomhessian";
	public static String CollisionProcess_SERVER_URL="http://lmv.bdip.17bim.com:8091/4DAnalog/remote/CollisionProcesshessian";
	public static String LvmVersion_SERVER_URL="http://lmv.bdip.17bim.com:8091/4DAnalog/remote/LvmVersionhessian";
	public static String ModelMessage_SERVER_URL="http://lmv.bdip.17bim.com:8091/4DAnalog/remote/modelMessagehessian";
	public static String Test_SERVER_URL="http://lmv.bdip.17bim.com:8091/4DAnalog/remote/testhessian";
	
	/** 是否开启Api访问, true表示调用远程Api接口， false表示调用数据库连接 **/
	public static boolean getApiEnable(){
		return API_ENABLE;
	}
	
	/** 是否开启Api访问, true表示调用远程Api接口， false表示调用数据库连接 **/
	public static boolean getSecurityEnable(){
		return SECURITY_ENABLE;
	}
	
	/** 获取api接口服务端URL **/
	public static String getDocApiServerUrl(){
		return Document_SERVER_URL;
	}
	
	public static String getFlowApiServerUrl(){
		return Flow_SERVER_URL;
	}
	public static String getTeamApiServerUrl(){
		return Team_SERVER_URL;
	}
	public static String getViewerSaveApiServerUrl(){
		return ViewerSave_SERVER_URL;
	}
}
