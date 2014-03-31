package com.tongwan.common.io.rpc.impl;

import static com.tongwan.common.io.rpc.MessageType.TYPE_BOOLEAN_TRUE;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;

import com.tongwan.common.io.rpc.RpcInput;
import com.tongwan.common.io.rpc.RpcVo;

/**
 * 网络数据包输入缓冲区
 * @author zhangde
 *
 * @date 2014年1月18日
 */
public class RpcInputNettyImpl implements RpcInput{
	private ChannelBuffer buffer;
	public RpcInputNettyImpl(ChannelBuffer buf){
		this.buffer=buf;
	}
	public int readInt(){
		return buffer.readInt();
	}
	public String readString(){
		int len=readInt();
		if(len>0){
			byte[] bytes=new byte[len];
			buffer.readBytes(bytes, 0, bytes.length);
			try {
				return new String(bytes,"utf8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	public long readLong(){
		return buffer.readLong();
	}
	public double readDouble(){
		return buffer.readDouble();
	}
	public boolean readBoolean(){
		byte _byte=buffer.readByte();
		if(_byte==TYPE_BOOLEAN_TRUE){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 
	 * @param gType List 泛型
	 * @return
	 */
	public <T> List<T> readList(Class<T> gType){
		int size=readInt();
		if(size>0){
			List<T> result=new ArrayList<>();
			for(int i=0;i<size;i++){
				result.add(readObject(gType));
			}
			return  result;
		}
		return null;
	}
	public byte[][] readByteArray2(){
		int size1=readInt();
		int size2=readInt();
		byte[][] bytes=new byte[size1][size2];
		for(int i=0;i<size1;i++){
			for(int j=0;j<size2;j++){
				bytes[i][j]=buffer.readByte();
			}
		}
		return bytes;
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
