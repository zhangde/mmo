package com.tongwan.common.io.rpc.impl;

import static com.tongwan.common.io.rpc.MessageType.TYPE_BOOLEAN_FALSE;
import static com.tongwan.common.io.rpc.MessageType.TYPE_BOOLEAN_TRUE;
import static com.tongwan.common.io.rpc.MessageType.TYPE_NULL;

import java.io.UnsupportedEncodingException;
import java.nio.ByteOrder;
import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import com.tongwan.common.io.rpc.RpcOutput;
import com.tongwan.common.io.rpc.RpcVo;

/**
 * 网络数据包输出缓冲区
 * @author zhangde
 *
 * @date 2014年1月18日
 */
public class RpcOutputNettyImpl implements RpcOutput{
	private ChannelBuffer buffer;
	public RpcOutputNettyImpl(){
		buffer=ChannelBuffers.dynamicBuffer(ByteOrder.LITTLE_ENDIAN, 1024);
	}
	public void writeObject(Object o){
		if(o==null){
			return;
		}
		if(o instanceof Integer){
			writeInt((int)o);
		}else if(o instanceof String){
			writeString((String)o);
		}else if(o instanceof Long){
			writeLong((long)o);
		}else if(o instanceof Double){
			writeDouble((double)o);
		}else if(o instanceof Boolean){
			writeBoolean((Boolean)o);
		}else if(o instanceof List){
			writeList((List)o);
		}else if(o instanceof RpcVo){
			((RpcVo)o).writeTo(this);
		}else{
			Class<?> clazz=o.getClass();
			if(clazz.isArray()){
				writeArray(o, clazz);
			}
		}
	}
	public void writeInt(int v){
		buffer.writeInt(v);
	}
	public void writeString(String v){
		int len=0;
		if(v!=null){
			try {
				byte[] bytes=v.getBytes("utf8");
				len=bytes.length;
				writeInt(len);
				buffer.writeBytes(bytes);
				return;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
		}
		writeInt(len);
	}
	public void writeLong(long v){
		buffer.writeLong(v);
	}
	public void writeDouble(double v){
		buffer.writeDouble(v);
	}
	public void writeBoolean(boolean v){
		if(v){
			buffer.writeByte(TYPE_BOOLEAN_TRUE);
		}else{
			buffer.writeByte(TYPE_BOOLEAN_FALSE);
		}
	}
	public <T> void writeList(List<T> v){
		if(v==null || v.isEmpty()){
			writeInt(0);
			return;
		}
		writeInt(v.size());//长度
		if(v!=null && !v.isEmpty()){
			for(T t:v){
				writeObject(t);
			}
		}
	}
	public void writeArray(Object array,Class componentType){
		if(array==null){
			writeInt(TYPE_NULL);
			return;
		}
		if(componentType.equals(byte[].class)){
			byte[] bytes=(byte[]) array;
			writeByteArray(bytes);
		}else if(componentType.equals(byte[][].class)){
			byte[][] bytes=(byte[][]) array;
			writeByteArray2(bytes);
		}
	}
	public void writeByteArray(byte[] bytes){
		writeInt(bytes.length);
		buffer.writeBytes(bytes);
	}
	public void writeByteArray2(byte[][] bytes){
		writeInt(bytes.length);
		writeInt(bytes[0].length);
		for(int i=0;i<bytes.length;i++){
			buffer.writeBytes(bytes[i]);
		}
	}
	public void writeRpcVo(RpcVo v){
		v.writeTo(this);
	}
	
	public byte[] toByteArray(){
//		buffer.discardReadBytes();
		int offset=buffer.readableBytes();
		byte[] bytes=new byte[offset];
		buffer.readBytes(bytes);
		return bytes;
	}
}
