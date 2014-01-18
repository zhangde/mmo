package com.tongwan.common.builder.rpc.io;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import com.tongwan.common.builder.rpc.RpcVo;
import com.tongwan.common.serialize.SimpleSerializeX;

/**
 * 网络数据包输出缓冲区
 * @author zhangde
 *
 * @date 2014年1月18日
 */
public class RpcOut {
	private ByteArrayOutputStream baos;
	private DataOutputStream os;
	public RpcOut(){
		baos=new ByteArrayOutputStream();
		os=new DataOutputStream(baos);
	}
	public void writeInt(int v){
		try {
			SimpleSerializeX.writeInt(v, os);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void writeString(String v){
		try {
			SimpleSerializeX.writeString(v, os);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void writeLong(long v){
		try {
			SimpleSerializeX.writeLong(v, os);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void writeDouble(double v){
		try {
			SimpleSerializeX.writeDouble(v, os);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void writeBoolean(boolean v){
		try {
			SimpleSerializeX.writeBoolean(v, os);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public <T> void writeArray(List<T> v){
		writeInt(v.size());//长度
		if(v!=null && !v.isEmpty()){
			for(T t:v){
				if(t instanceof RpcVo){
					((RpcVo)t).writeTo(this);
				}else{
					try {
						SimpleSerializeX.writeObject(t, os);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	public void writeRpcVo(RpcVo v){
		v.writeTo(this);
	}
}
