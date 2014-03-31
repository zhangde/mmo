package com.tongwan.common.io.rpc;

import com.tongwan.common.io.rpc.impl.RpcInputNettyImpl;
import com.tongwan.common.io.rpc.impl.RpcOutputNettyImpl;

/**
 * 网络传输数据结构接口
 * @author zhangde
 *
 * @date 2014年1月18日
 */
public interface RpcVo {
	public void writeTo(RpcOutputNettyImpl buffer);
	public void read(RpcInputNettyImpl in);
}
