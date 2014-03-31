package com.tongwan.common.io.rpc.impl;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import com.tongwan.common.io.rpc.RpcVo;

import static com.tongwan.common.io.rpc.MessageType.*;

import com.tongwan.common.serialize.SimpleSerializeX;

/**
 * 网络数据包输出缓冲区
 * @author zhangde
 *
 * @date 2014年1月18日
 */
public class RpcOutputNettyImpl {
	private ByteArrayOutputStream baos;
	private DataOutputStream os;
	public RpcOutputNettyImpl(){
		baos=new ByteArrayOutputStream();
		os=new DataOutputStream(baos);
	}
	public void writeObject(Object o){
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
		try {
			os.writeInt(v);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void writeString(String v){
		try {
			os.writeUTF(v);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void writeLong(long v){
		try {
			os.writeLong(v);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void writeDouble(double v){
		try {
			os.writeDouble(v);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void writeBoolean(boolean v){
		try {
			if(v){
				os.writeByte(TYPE_BOOLEAN_TRUE);
			}else{
				os.writeByte(TYPE_BOOLEAN_FALSE);
			}
		} catch (IOException e) {
			e.printStackTrace();
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
		try {
			os.writeInt(bytes.length);
			os.write(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void writeByteArray2(byte[][] bytes){
		try {
			os.writeInt(bytes.length);
			os.writeInt(bytes[0].length);
			for(int i=0;i<bytes.length;i++){
				os.write(bytes[i]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void writeRpcVo(RpcVo v){
		v.writeTo(this);
	}
	
	public byte[] toByteArray(){
		return baos.toByteArray();
	}
}
