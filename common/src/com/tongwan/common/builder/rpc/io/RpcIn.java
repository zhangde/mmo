package com.tongwan.common.builder.rpc.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.tongwan.common.builder.rpc.RpcVo;
import com.tongwan.common.serialize.SimpleSerializeX;

/**
 * 网络数据包输入缓冲区
 * @author zhangde
 *
 * @date 2014年1月18日
 */
public class RpcIn {
	private DataInputStream is;
	public RpcIn(InputStream is){
		is=new DataInputStream(is);
	}
	public int readInt(){
		try {
			return SimpleSerializeX.readInt(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public String readString(){
		try {
			return SimpleSerializeX.readString(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public long readLong(){
		try {
			return SimpleSerializeX.readLong(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public double readDouble(){
		try {
			return SimpleSerializeX.readDouble(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public boolean readBoolean(){
		try {
			return SimpleSerializeX.readBoolean(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public <T> T readObject(Class<T> clazz){
		if(clazz.isAssignableFrom(RpcVo.class)){
			try {
				T t=clazz.newInstance();
				RpcVo vo=(RpcVo) t;
				vo.read(this);
				return t;
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
