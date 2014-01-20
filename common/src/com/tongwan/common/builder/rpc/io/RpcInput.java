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
import static com.tongwan.common.builder.rpc.io.MessageType.*;

/**
 * 网络数据包输入缓冲区
 * @author zhangde
 *
 * @date 2014年1月18日
 */
public class RpcInput {
	private DataInputStream is;
	public RpcInput(byte[] buf){
		this.is=new DataInputStream(new ByteArrayInputStream(buf));
	}
	public int readInt(){
		try {
			return is.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public String readString(){
		try {
			return is.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public long readLong(){
		try {
			return is.readLong();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public double readDouble(){
		try {
			return is.readDouble();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public boolean readBoolean(){
		try {
			byte _byte=is.readByte();
			if(_byte==TYPE_BOOLEAN_TRUE){
				return true;
			}else{
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 
	 * @param gType List 泛型
	 * @return
	 */
	public <T> List<T> readList(Class<T> gType){
		try {
			int size=is.readInt();
			if(size>0){
				List<T> result=new ArrayList<>();
				for(int i=0;i<size;i++){
					result.add(readObject(gType));
				}
				return  result;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public byte[][] readByteArray2(){
		try {
			int size1=is.readInt();
			int size2=is.readInt();
			byte[][] bytes=new byte[size1][size2];
			for(int i=0;i<size1;i++){
				for(int j=0;j<size2;j++){
					bytes[i][j]=is.readByte();
				}
			}
			return bytes;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
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
