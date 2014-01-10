package com.tongwan.common.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
/**
 * @author zhangde
 * 
 * @date 2014Äê1ÔÂ9ÈÕ
 */
public class SimpleSerializeX {
	private static byte TYPE_INT=0;
	private static byte TYPE_STRING=1;
	private static byte TYPE_DOUBLE=2;
	private static byte TYPE_LONG=3;
	private static byte TYPE_MAP=4;
	private static byte TYPE_ARRAY=5;
	public static void writeInt(int v,DataOutputStream os) throws IOException{
		os.writeByte(TYPE_INT);
		os.writeInt(v);
	}
	public static void writeString(String v,DataOutputStream os) throws IOException{
		os.writeByte(TYPE_STRING);
		byte[] bytes=v.getBytes("utf8");
		os.writeInt(bytes.length);
		os.write(bytes);
	}
	public static void writeDouble(double v,DataOutputStream os) throws IOException{
		os.writeByte(TYPE_DOUBLE);
		os.writeDouble(v);
	}
	public static void writeLong(long v,DataOutputStream os) throws IOException{
		os.writeByte(TYPE_LONG);
		os.writeLong(v);
	}
	public static void writeObject(Object v,DataOutputStream os) throws IOException{
		if(v instanceof Integer){
			writeInt((int)v, os);
		}else if(v instanceof Double){
			writeDouble((double)v, os);
		}else if(v instanceof Long){
			writeLong((long)v, os);
		}else if(v instanceof String){
			writeString((String)v, os);
		}else if(v instanceof Map){
			writeMap((Map)v, os);
		}
	}
	public static void writeMap(Map v,DataOutputStream os) throws IOException{
		os.writeByte(TYPE_MAP);
		Set keySet=v.keySet();
		Iterator keyIterator=keySet.iterator();
		os.writeInt(keySet.size());
		while(keyIterator.hasNext()){
			Object key=keyIterator.next();
			Object value=v.get(key);
			writeObject(key, os);
			writeObject(value, os);
		}
	}
	public static void writeArray(Object[] v,DataOutputStream os) throws IOException{
		os.writeByte(TYPE_ARRAY);
		os.writeInt(v.length);
		for(Object o :v){
			writeObject(o, os);
		}
	}
	public static Object readObject(DataInputStream dis) throws IOException{
		System.out.println(dis.available());
		byte type=dis.readByte();
		if(type==TYPE_INT){
			return dis.readInt();
		}else if(type==TYPE_DOUBLE){
			return dis.readDouble();
		}else if(type==TYPE_LONG){
			return dis.readLong();
		}else if(type==TYPE_STRING){
			int len=dis.readInt();
			byte[] s=new byte[len];
			dis.readFully(s);
			return new String(s);
		}else if(type==TYPE_MAP){
			int size=dis.readInt();
			Map result=new HashMap<>();
			for(int i=0;i<size;i++){
				Object key=readObject(dis);
				Object value=readObject(dis);
				result.put(key, value);
			}
			return result;
		}else if(type==TYPE_ARRAY){
			int size=dis.readInt();
			Object[] o=new Object[size];
			for(int i=0;i<size;i++){
				o[i]=readObject(dis);
			}
			return o;
		}
		throw new RuntimeException("unkonw type "+type);
	}
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(baos);
//		long a=121123123;
//		SerializeX.writeObject(a, dos);
		Map map=new HashMap<>();
		map.put("name", "zd");
		map.put("xx", 1);
		map.put("xx2", 1.1);
		map.put("xx3", 1123123l);
		SimpleSerializeX.writeMap(map, dos);
		
		byte[] bytes=baos.toByteArray();
		System.out.println();
		for(byte b:bytes){
			System.out.print(b+"_");
		}
		System.out.println();
		ByteArrayInputStream bais=new ByteArrayInputStream(bytes);
		DataInputStream dis=new DataInputStream(bais);
		Map result=(Map) SimpleSerializeX.readObject(dis);
		System.out.println(result);
	}

}
