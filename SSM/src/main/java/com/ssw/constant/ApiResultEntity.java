package com.ssw.constant;

import java.io.Serializable;

public class ApiResultEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	  private String code = "";
	  private String msg = "";
	  private Object data = "";
	  private Object dataObject;
	  private String version;
	  private boolean dataEncode;

	/**
	   * 设置操作成功返回值结果ode=200;msg=接口调用成功
	   */
	  public ApiResultEntity setSuccessCodeAndMsg() {
		setCode(ConstantDefine.SUCCESS_CODE_200);
		setMsg(ConstantDefine.SUCCESS_MASSAGE_200);
		return this;
	}
	  /**
	   * 设置操作成功,并设置数据到返回值结果code=200;msg=接口调用成功;data=obj
	   */
	  public ApiResultEntity setSuccessCodeAndData(Object obj) {
		setCode(ConstantDefine.SUCCESS_CODE_200);
		setMsg(ConstantDefine.SUCCESS_MASSAGE_200);
		setData(obj);
		return this;
	  }
	  /**
	   * 设置操作成功,但是返回结果为空,返回值结果code=202;msg=查询数据为空
	   */
	  public ApiResultEntity setEmptySuccessCodeAndMsg() {
		  setCode(ConstantDefine.SUCCESS_CODE_202);
		  setMsg(ConstantDefine.SUCCESS_MSG_202);
		  return this;
	  }
	  /**
	   * 设置参数传输错误,返回值结果code = 502 ; msg = 参数错误
	   */
	  public ApiResultEntity setParamErrorCodeAndMsg() {
		  setCode(ConstantDefine.ERROR_CODE_502);
		  setMsg(ConstantDefine.ERROR_MASSAGE_502);
		  return this;
	  }
	  /**
	   * 设置服务端出现异常,返回值结果code = 500 ; msg = 服务端报错
	   */
	  public ApiResultEntity setServerErrorCodeAndMsg() {
		  setCode(ConstantDefine.ERROR_CODE_500);
		  setMsg(ConstantDefine.ERROR_MASSAGE_500);
		  return this;
	  }
	  /**
	   * 设置服务端出现异常,返回值结果code = 500 ; msg = 数据库操作异常
	   */
	  public ApiResultEntity setSQLErrorCodeAndMsg() {
		  setCode(ConstantDefine.ERROR_CODE_500);
		  setMsg(ConstantDefine.ERROR_MSG_SQL);
		  return this;
	  }
	  
	  public ApiResultEntity appendMsg(String msg) {
		  setMsg(getMsg()+"附加消息:"+msg);
		  return this;
	  }
	  
	  public String getCode()
	  {
	    return this.code;
	  }
	  public ApiResultEntity(String code, String msg, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	public ApiResultEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void setCode(String code) {
	    this.code = code;
	  }
	  public String getMsg() {
	    return this.msg;
	  }
	  public void setMsg(String msg) {
	    this.msg = msg;
	  }
	  public Object getData() {
	    return this.data;
	  }
	  public void setData(Object data) {
	    this.data = data;
	  }

	  public Object getDataObject()
	  {
	    return this.dataObject;
	  }
	  public void setDataObject(Object dataObject) {
	    this.dataObject = dataObject;
	  }
	  public boolean isDataEncode() {
	    return this.dataEncode;
	  }
	  public void setDataEncode(boolean dataEncode) {
	    this.dataEncode = dataEncode;
	  }
	  public String getVersion() {
	    return this.version;
	  }
	  public void setVersion(String version) {
	    this.version = version;
	  }

	  public boolean readIsOk() {
	    return "200".equalsIgnoreCase(this.code);
	  }

	  public ApiResultEntity let100()
	  {
	    this.code = "100";

	    return this;
	  }

	  public ApiResultEntity let102() {
	    this.code = "102";

	    return this;
	  }

	  public ApiResultEntity let204()
	  {
	    this.code = "204";

	    return this;
	  }

	  public ApiResultEntity let410()
	  {
	    this.code = "410";

	    return this;
	  }

	  public ApiResultEntity let400()
	  {
	    this.code = "400";

	    return this;
	  }

	  public ApiResultEntity let500()
	  {
	    this.code = "500";

	    return this;
	  }

	  public ApiResultEntity let103() {
	    this.code = "103";

	    return this;
	  }

	  public void letIsOk(boolean isOk) {
	    if (isOk)
	      this.code = "200";
	    else
	      this.code = "fail";
	  }
	@Override
	public String toString() {
		return "ApiResultEntity [code=" + code + ", msg=" + msg + ", data=" + data + ", dataObject=" + dataObject
				+ ", version=" + version + ", dataEncode=" + dataEncode + "]";
	}
}
