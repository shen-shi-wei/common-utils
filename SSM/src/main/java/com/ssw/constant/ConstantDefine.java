package com.ssw.constant;

import java.math.BigDecimal;

public class ConstantDefine {

    /**
     * 错误代码_code_500：服务端报错
     **/
    public static String ERROR_CODE_500 = "500";
    /**
     * 错误代码_code_501：账号或密码错误
     **/
    public static String ERROR_CODE_501 = "501";
    /**
     * 错误代码_code_502：参数错误
     **/
    public static String ERROR_CODE_502 = "502";
    /**
     * 错误代码_code_503：数据保存失败
     **/
    public static String ERROR_CODE_503 = "503";
    /**
     * 错误代码_code_504：您的账号没有访问权限
     **/
    public static String ERROR_CODE_504 = "504";
    /**
     * 错误代码_code_505：房间关闭
     **/
    public static String ERROR_CODE_505 = "505";
    /**
     * 错误消息_code_506：名称重复
     **/
    public static String ERROR_CODE_506 = "506";

    /**
     * 错误消息_massage_500：服务端报错
     **/
    public static String ERROR_MASSAGE_500 = "服务端报错";
    /**
     * 错误消息_massage_501：账号或密码错误
     **/
    public static String ERROR_MASSAGE_501 = "账号或密码错误";
    /**
     * 错误消息_massage_502：参数错误
     **/
    public static String ERROR_MASSAGE_502 = "参数错误";
    /**
     * 错误消息_massage_503：数据保存失败
     **/
    public static String ERROR_MASSAGE_503 = "数据保存失败";
    /**
     * 错误消息_massage_504：您的账号没有访问权限
     **/
    public static String ERROR_MASSAGE_504 = "您的账号没有访问权限";
    /**
     * 错误消息_massage_505：房间关闭
     **/
    public static String ERROR_MASSAGE_505 = "房间关闭";
    /**
     * 错误消息_massage_506：名称重复
     **/
    public static String ERROR_MASSAGE_506 = "名称重复";

    /**
     * 请求成功_code_200
     **/
    public static String SUCCESS_CODE_200 = "200";
    /**
     * 请求成功_massage_200
     **/
    public static String SUCCESS_MASSAGE_200 = "接口调用成功";

    /**
     * 文件类型后缀：.zip .rar
     **/
    public static String FileType_Ext_ZIP = ".zip";
    /**
     * 文件类型后缀：.zip .rar
     **/
    public static String FileType_Ext_RAR = ".rar";
    /**
     * 模型文件类型后缀：.f2d(2D) .svf(3D)
     **/
    public static String ModelFileType_Ext_2D = ".f2d";
    /**
     * 模型文件类型后缀：.f2d(2D) .svf(3D)
     **/
    public static String ModelFileType_Ext_3D = ".svf";
    /**
     * 模型文件类型后缀：.f2d(2D) .svf(3D)
     **/
    public static String ModelFileType_Ext_SDB = ".sdb";

    /**
     * 分隔符：.
     **/
    public static String SEP_dot = ".";
    /**
     * 分隔符：_
     **/
    public static String SEP__ = "_";
    /**
     * 分隔符：__
     **/
    public static String SEP___ = "__";

    /**
     * 分隔符 空格
     */
    public static final String SEP_0 = "";
    /**
     * 分隔符 __
     */
    public static final String SEP_1 = "__";
    /**
     * 分隔符 _
     */
    public static final String SEP_2 = "_";
    /**
     * 分隔符 /
     */
    public static final String SEP_3 = "/";
    /**
     * 分隔符 .
     */
    public static final String SEP_4 = ".";
    /**
     * 分隔符 <
     */
    public static final String SEP_5 = "<";
    /**
     * 分隔符 >
     */
    public static final String SEP_6 = ">";
    /**
     * 分隔符 (
     */
    public static final String SEP_7 = "(";
    /**
     * 分隔符 空格
     */
    public static final String SEP_8 = " ";
    /**
     * 分隔符 ;
     */
    public static final String SEP_9 = ";";
    /**
     * 分隔符 ,
     */
    public static final String SEP_10 = ",";
    /**
     * 分隔符 -
     */
    public static final String SEP_11 = " - ";
    /**
     * 分隔符 &
     */
    public static final String SEP_12 = "&";
    /**
     * 分隔符 =
     */
    public static final String SEP_13 = "=";
    /**
     * 分隔符 --
     */
    public static final String SEP_14 = "--";
    /**
     * 分隔符 ?
     */
    public static final String SEP_15 = "?";
    /**
     * 结尾符 );
     */
    public static final String END_1 = ");";
    /**
     * 结尾符 ==
     */
    public static final String END_2 = "==";
    /**
     * 结尾符 =
     */
    public static final String END_3 = "=";
    /**
     * 结尾符 ==\r\n
     */
    public static final String END_4w = "==\r\n";
    /**
     * 结尾符 =\r\n
     */
    public static final String END_5w = "=\r\n";
    /**
     * 结尾符 ==\n
     */
    public static final String END_4 = "==\n";
    /**
     * 结尾符 =\n
     */
    public static final String END_5 = "=\n";

    /**
     * jsonpCallback(
     */
    public static final String JsonpCallback = "jsonpCallback(";
    /**
     * jsonp文件前缀
     */
    public static final String JsonpFileNamePre = "jsonp_";

    public static final Integer Boolean_1_true = 1;
    public static final Integer Boolean_0_false = 0;
    public static final String Encode_0_false = "0";
    public static final String Encode_1_true = "0";

    public static final String Transfer_Dot = "\\.";

    /**
     * ZeroBigDecimal
     **/
    public static final BigDecimal ZeroBigDecimal = new BigDecimal(0);
    /**
     * BigDecimal 1000
     **/
    public static final BigDecimal BigDecimal1000 = new BigDecimal(1000);

    /**
     * Api请求结果代码：失败 fail
     */
    public static final String ApiCallResult_Code_fail = "fail";
    /**
     * Api请求结果代码：100
     */
    public static final String ApiCallResult_Code_100 = "100";
    /**
     * Api请求结果代码：102
     */
    public static final String ApiCallResult_Code_102 = "102";
    /**
     * 充值成功：200或操作完成
     */
    public static final String ApiCallResult_Code_200 = "200";
    /**
     * 内部服务器错误：500
     */
    public static final String ApiCallResult_Code_500 = "500";
    public static final String ApiCallResult_Code_103 = "103";
    public static final String ApiCallResult_Code_201 = "201";
    public static final String ApiCallResult_Code_510 = "510";

    /**
     * 键：jsonpCallback
     **/
    public static final String Key_jsonpCallback = "jsonpCallback";

    public static String HTML_UTF8 = "utf-8";

    public static String CHAR_UTF8 = "UTF-8";

    public static String HTML_ContentType_UTF8 = "text/html;charset=UTF-8";

    public static String Boolean_true_1 = "1";

    public static String Boolean_true_true = "true";

    public static String Boolean_false_0 = "0";

    public static String Boolean_false_false = "false";

    public static String Boolean_ok_200 = "200";

    public static String Boolean_ok_ok = "ok";

    public static String Message_Status_Read = "1";

    public static String File_Suffix_Pdf = "pdf";

    /**
     * 批处理sql状态： 0开始,1成功,2失败
     **/
    public static int BATCH_STATE_0_INIT = 0;
    /**
     * 批处理sql状态： 0开始,1成功,2失败
     **/
    public static int BATCH_STATE_1_SUCCESS = 1;
    /**
     * 批处理sql状态： 0开始,1成功,2失败
     **/
    public static int BATCH_STATE_2_FAIL = 2;

    public static final String Language_ZH_CN = "ZH_CN";
    public static final String Language_EN = "EN";
    public static final String Language_ZH_TW = "ZH_TW";

    public static final int cache = 100;

    public static final String SUCCESS_CODE_202 = "202";
    public static final String SUCCESS_MSG_202= "查询数据为空";
    public static final String ERROR_MSG_SQL = "数据库操作异常";


}
