package com.musicfire.common.utiles;

public class Result {
 
	private Integer code;//状态码
	private Boolean isSuccess;//状态
	private String message;//消息
	private Object result;//数据对象

	/**
	 * 无参构造器
	 */
	public Result(){
		super();
	}

	/**
	 * 只返回状态，状态码，消息
	 * @param success
	 * @param code
	 * @param message
	 */
	public Result(Boolean success, Integer code, String message){
		super();
		this.isSuccess=success;
		this.code=code;
		this.message=message;
	}

	/**
	 * 只返回状态，状态码，数据对象
	 * @param success
	 * @param code
	 * @param result
	 */
	public Result(Boolean success, Integer code, Object result){
		super();
		this.isSuccess=success;
		this.code=code;
		this.result=result;
	}

	/**
	 * 返回全部信息即状态，状态码，消息，数据对象
	 * @param success
	 * @param code
	 * @param message
	 * @param result
	 */
	public Result(Boolean success, Integer code, String message, Object result){
		super();
		this.isSuccess=success;
		this.code=code;
		this.message=message;
		this.result=result;
	}
	public Result ok(Object result){
		this.isSuccess=true;
		this.code=0;
		this.message="成功";
		this.result=result;
		return this;
	}
	public Result ok(){
		this.isSuccess=true;
		this.code=0;
		this.message="成功";
		return this;
	}
	public Result fail(Integer code,String message){
		this.isSuccess=false;
		this.code=code;
		this.message=message;
		return this;
	}
	public Result fail(String message){
		this.isSuccess=false;
		this.code=-1;
		this.message=message;
		return this;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
	
}