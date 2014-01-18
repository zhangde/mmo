package com.tongwan.common.builder.rpc;

import java.util.List;

/**
 * 网络数据包缓冲区
 * @author zhangde
 *
 * @date 2014年1月18日
 */
public class RpcBuffer {
	public void writeInt(int v){}
	public void writeString(String v){}
	public void writeLong(long v){}
	public void writeDouble(double v){}
	public void writeFloat(float v){}
	public void writeBoolean(boolean v){}
	public <T> void writeArray(List<T> v){
		writeInt(v.size());//长度
		if(v!=null && !v.isEmpty()){
			for(T t:v){
				
			}
		}
	}
	public void writeRpcVo(RpcVo v){
		v.writeTo(this);
	}
}
