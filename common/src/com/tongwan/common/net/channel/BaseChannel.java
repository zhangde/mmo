package com.tongwan.common.net.channel;

import com.tongwan.common.builder.rpc.RpcVo;
import com.tongwan.common.builder.rpc.io.RpcOutput;
import com.tongwan.common.net.ResultObject;

/**
 * 网络流通道基础接口
 * @author zhangde
 *
 * @date 2014年1月18日
 */
public interface BaseChannel {
	/**
	 * 客户端向服务端写数据
	 * @param out
	 */
	public void writeRpcOutput(RpcOutput out);
	/**
	 * 服务端向客户端写数据
	 * @param resultObject
	 */
	public void writeResultObject(ResultObject resultObject);
}
