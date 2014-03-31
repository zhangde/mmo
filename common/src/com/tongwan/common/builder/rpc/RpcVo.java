package com.tongwan.common.builder.rpc;

import com.tongwan.common.builder.rpc.io.RpcInputNettyImpl;
import com.tongwan.common.builder.rpc.io.RpcOutput;

/**
 * 网络传输数据结构接口
 * @author zhangde
 *
 * @date 2014年1月18日
 */
public interface RpcVo {
	public void writeTo(RpcOutput buffer);
	public void read(RpcInputNettyImpl in);
}
