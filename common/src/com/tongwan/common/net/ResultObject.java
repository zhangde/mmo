package com.tongwan.common.net;

/**
 * 返回值对象
 * @author zhangde
 *
 * @date 2014年1月18日
 */
public class ResultObject<T> {
	/** 模块号 */
	private int module;
	/** 指令号 */
	private int cmd;
	/** 返回状态 */
	private int result;
	/** 返回值*/
	private T value;
	
	public static ResultObject valueOf(int module,int cmd){
		ResultObject resultObject = new ResultObject();
		resultObject.module=module;
		resultObject.cmd=cmd;
		return resultObject;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
	public int getCmd() {
		return cmd;
	}
	public void setCmd(int cmd) {
		this.cmd = cmd;
	}
	public int getModule() {
		return module;
	}
	public void setModule(int module) {
		this.module = module;
	}
	
}
