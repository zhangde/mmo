package com.tongwan.common.builder.rpc;

/**
 * 网络传输数据结构接口
 * @author zhangde
 *
 * @date 2014年1月18日
 */
public interface RpcVo {
	public void writeTo(RpcBuffer buffer);
}
