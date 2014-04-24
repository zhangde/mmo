package com.tongwan.common.builder.rpc;

/**
 * 远程方法调用数据传输方式 
 * @author zhangde
 * @date 2014年4月21日
 */
public enum RPCTMode {
	/** 请求并且有数据返回  */
	SEND_RETURN,
	
	/** 请求，数据返回 ，且可推送 */
	SEND_RETURN_PUSH,
	
	/** 请求无返回  */
	ONLY_SEND,
	
	/** 只推送 */
	ONLY_PUSH
	
}
