package com.tongwan.common.net.channel;

import com.tongwan.common.net.ResultObject;

/**
 * 网络流通道基础接口
 * @author zhangde
 *
 * @date 2014年1月18日
 */
public interface BaseChannel {
	public int readInt();
	public String readString();
	public <T> void writeResultObject(ResultObject<T> resultObject);
}
