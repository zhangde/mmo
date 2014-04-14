package com.tongwan.common.io.rpc;

import java.util.List;

/**
 * 网络数据包输出缓冲区
 * @author zhangde
 *
 * @date 2014年1月18日
 */
public interface RpcOutput {
	public void writeObject(Object o);
	public void writeInt(int v);
	public void writeString(String v);
	public void writeLong(long v);
	public void writeDouble(double v);
	public void writeBoolean(boolean v);
	public <T> void writeList(List<T> v);
	public void writeArray(Object array,Class componentType);
	public void writeByteArray(byte[] bytes);
	public void writeByteArray2(byte[][] bytes);
	public void writeIntArray(int[] v);
	public void writeRpcVo(RpcVo v);
	public byte[] toByteArray();
}
