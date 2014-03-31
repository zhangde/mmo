package com.tongwan.common.net.channel;

import com.tongwan.common.io.rpc.RpcOutput;
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
	/**
	 * 设置用户自定义对象
	 * @param attachment
	 */
	public void setAttachment(Object attachment);
	/**
	 * 取得用户自定义对象
	 * @return
	 */
	public Object getAttachment();
	/**
	 * 取得连接的唯 一标识
	 * @return
	 */
	public int getId();
	/**
	 * 关闭通道
	 */
	public void close();
}
