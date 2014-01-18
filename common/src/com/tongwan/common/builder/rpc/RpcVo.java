package com.tongwan.common.builder.rpc;

import com.tongwan.common.builder.rpc.io.RpcIn;
import com.tongwan.common.builder.rpc.io.RpcOut;

/**
 * 网络传输数据结构接口
 * @author zhangde
 *
 * @date 2014年1月18日
 */
public interface RpcVo {
	public void writeTo(RpcOut buffer);
	public void read(RpcIn in);
}
