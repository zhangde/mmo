package com.tongwan.common.io.rpc;

import java.util.List;

/**
 * 网络数据包输入缓冲区
 * @author zhangde
 *
 * @date 2014年1月18日
 */
public interface RpcInput {
	public int readInt();
	public String readString();
	public long readLong();
	public double readDouble();
	public boolean readBoolean();
	public <T> List<T> readList(Class<T> gType);
	public byte[][] readByteArray2();
	public <T> T readObject(Class<T> clazz);
}
