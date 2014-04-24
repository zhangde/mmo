/**
 * 
 */
package com.tongwan.common.builder.rpc;

import static com.tongwan.common.lang.TypeX.isBoolean;
import static com.tongwan.common.lang.TypeX.isDouble;
import static com.tongwan.common.lang.TypeX.isInt;
import static com.tongwan.common.lang.TypeX.isIntArray;
import static com.tongwan.common.lang.TypeX.isList;
import static com.tongwan.common.lang.TypeX.isLong;
import static com.tongwan.common.lang.TypeX.isObjectArray;
import static com.tongwan.common.lang.TypeX.isString;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangde
 * @date 2013-9-24
 */
public class ServiceG {
	class UserVO{
		public int id;
		public String name;
		public PlayerVO playerVO;
		List<PlayerBattleVO> playerBattleVOs;
	}
	class PlayerVO{
		public int golden;
		public int silver;
	}
	class PlayerBattleVO{
		public int id;
		public int level;
	}
	interface RpcInterface{
//		@RpcMethodTag(cmd=1,push=false,params={"name","password"},remark="登陆")
//		public ResultObject<UserVO> login(String name,String password);
//		@RpcMethodTag(cmd=2,push=false,params={"name"},remark="封号")
//		public ResultObject<UserVO> closeUser(String name);
	}
	
	public static void main(String[] args) throws Exception {
		serverDataStruct("gen.data",ServiceG.class);
		client4CSharpDataStruct("gen.data",ServiceG.class);
//		Class g=ServiceG.class;
//		Class[] classes=g.getDeclaredClasses();
//		for(Class c:classes){
//			if(!c.isInterface()){
//				System.out.println(toClassFile(c, "gen"));
//				
//			}
//		}
		Class clazz=RpcInterface.class;
		Method[] ms =clazz.getDeclaredMethods();
		List<RpcMethod> methods=new ArrayList<>();
		for(Method m:ms){
			methods.add(new RpcMethod(m));
		}
		server(0,"gen.service",clazz.getSimpleName(),methods);
		client4Java("gen.client", methods);
		client4CSharp(0,RpcInterface.class,"gen.client",methods);
		clientJavaScript("gen", methods);
	}
	public static void client4CSharpDataStruct(String pg,Class g) throws Exception{
		StringBuffer sb=new StringBuffer();
		l(sb,"using System.Collections;");
		l(sb,"using System.Collections.Generic;");
		l(sb,"public class Vos{");
		String path = "src/"+pg.replaceAll("[.]", "/");
		File dir=new File(path);
		if(!dir.exists()){
			System.out.println(dir.mkdirs());
		}
		Class[] classes=g.getDeclaredClasses();
		for(Class c:classes){
			if(!c.isInterface()){
				String content=toClientClassFile(c, pg);
				l(sb,content);
			}
		}
		l(sb,"}");
		File f=new File(path+"/Vos.cs");
		FileOutputStream fos=new FileOutputStream(f);
		fos.write(sb.toString().getBytes("utf8"));
		fos.flush();
		fos.close();
	}
	public static void serverDataStruct(String pg,Class g) throws Exception{
		String path = "src/"+pg.replaceAll("[.]", "/");
		File dir=new File(path);
		if(!dir.exists()){
			System.out.println(dir.mkdirs());
		}
		Class[] classes=g.getDeclaredClasses();
		for(Class c:classes){
			if(!c.isInterface()){
				String content=toClassFile(c, pg);
				File f=new File(path+"/"+c.getSimpleName()+".java");
				System.out.println(f.getPath());
				if(!f.exists()){
					f.createNewFile();
				}
				FileOutputStream fos=new FileOutputStream(f);
				fos.write(content.getBytes("utf8"));
				fos.flush();
				fos.close();
			}
		}
	}
	public static void server(int module,String pg,String className,List<RpcMethod> methods) throws Exception{
		StringBuffer sb=new StringBuffer();
		l(sb,"package %s;",pg);
		
		l(sb,"import gen.data.*;");
		l(sb,"import com.tongwan.common.net.ResultObject;");
		l(sb,"import com.tongwan.common.net.channel.BaseChannel;");
		l(sb,"import com.tongwan.common.io.rpc.*;");
		l(sb,"public abstract class %s extends com.tongwan.net.Handler{",className);
		l(sb,"	protected int getModule(){");
		l(sb,"		return %d;",module);
		l(sb,"	}");
		l(sb,"	public void process(int cmd,BaseChannel channel,RpcInput in) throws Exception{");
//		l(sb,"		Map parame=channel.getParame();");
//		l(sb,"		int cmd=in.readInt(); //指令编号");
		l(sb,"		int sn=in.readInt();  //指令序号");
		l(sb,"		switch(cmd){");
		for(RpcMethod m:methods){
			if(m.getTransportMode()!=RPCTMode.ONLY_PUSH){
				l(sb,"			case %s :{",m.getTag().cmd());
				l(sb,"				_%s(channel,in,sn);",m.getM().getName());
				l(sb,"				return;");
				l(sb,"			}");
			}
		}
		l(sb,"		}");
		l(sb,"		throw new RuntimeException(\" cmd: \" + cmd + \" not found processor.\");");
		l(sb,"	}");
		for(RpcMethod m:methods){
			if(m.getTransportMode()!=RPCTMode.ONLY_PUSH){
				l(sb,m.toInner(module));
			}
		}
		for(RpcMethod m:methods){
			if(m.getTransportMode()!=RPCTMode.ONLY_PUSH){
				l(sb,m.toAbstract());
			}
		}
		for(RpcMethod m:methods){
			if(m.getTransportMode()==RPCTMode.ONLY_PUSH || m.getTransportMode()==RPCTMode.SEND_RETURN_PUSH){
				l(sb,m.toPush(module));
			}
		}
		
//		l(sb,"}");
//		l(sb,"	public void writeJson(Channel channel,Map map,String callback) throws Exception{");
//		l(sb,"		HttpResponse httpResponse = new DefaultHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK);");
//		l(sb,"		String data = JSON.toJSONString(map);");
//		l(sb,"		byte[] content = (callback+\"(\"+data+\")\").getBytes();");
//		l(sb,"		ChannelBuffer cb = ChannelBuffers.buffer(content.length);");
//		l(sb,"		cb.writeBytes(content);");
//		l(sb,"		httpResponse.setContent(cb);");
//		l(sb,"		httpResponse.addHeader(\"Content-Type\", \"application/json; charset=UTF-8\");");
//		l(sb,"		channel.write(httpResponse);");
//		l(sb,"		channel.disconnect();");
//		l(sb,"		channel.close();");
//		l(sb,"	}");
		l(sb,"}");
		String path=pg.replaceAll("[.]","/");
		File file=new File("src/"+path);
		if(!file.exists()){
			file.mkdirs();
		}
		File clazzFile=new File("src/"+path+"/"+className+".java");
		if(!clazzFile.exists()){
			clazzFile.createNewFile();
		}
		FileOutputStream fis=new FileOutputStream(clazzFile);
		fis.write(sb.toString().getBytes("utf8"));
		fis.flush();
		fis.close();
		System.out.println(sb);
		
	}
	public static void client4Java(String pg,List<RpcMethod> methods) throws Exception{
		StringBuffer sb=new StringBuffer();
		l(sb,"package %s;",pg);
		
		l(sb,"import gen.data.*;");
		l(sb,"import com.tongwan.common.net.ResultObject;");
		l(sb,"import com.tongwan.common.net.channel.BaseChannel;");
		l(sb,"import com.tongwan.common.io.rpc.*;");
		l(sb,"import com.tongwan.common.io.rpc.impl.*;");
		l(sb,"public abstract class RpcClient {");
		l(sb,"	protected BaseChannel channel;");
		l(sb,"	private int sn=0;");
		l(sb,"	public void dispath(RpcInput in)throws Exception{");
		l(sb,"		int cmd=in.readInt();");
		l(sb,"		switch(cmd){");
		for(RpcMethod m:methods){
			if(!m.getReturnType2().equals("void")){
				l(sb,"			case %s :{",m.getTag().cmd());
				l(sb,"				_%s(in,sn);",m.getM().getName());
				l(sb,"				return;");
				l(sb,"			}");
			}
			
		}
		l(sb,"		}");
		l(sb,"	}");
		//请求方法
		for(RpcMethod m:methods){
			l(sb,m.toClient4Java());
		}
		//内部解析数据格式方法
		for(RpcMethod m:methods){
			l(sb,m.toJavaClientInner());
		}
		for(RpcMethod m:methods){
			l(sb,m.toJavaClientAbstract());
		}
		l(sb,"}");
		String path=pg.replaceAll("[.]","/");
		File file=new File("src/"+path);
		if(!file.exists()){
			file.mkdirs();
		}
		File clazzFile=new File("src/"+path+"/RpcClient.java");
		if(!clazzFile.exists()){
			clazzFile.createNewFile();
		}
		FileOutputStream fis=new FileOutputStream(clazzFile);
		fis.write(sb.toString().getBytes("utf8"));
		fis.flush();
		fis.close();
		System.out.println(sb);
		
	}
	
	public static void client4CSharp(int module,Class clazz,String pg,List<RpcMethod> methods) throws Exception{
		StringBuffer sb=new StringBuffer();
		//l(sb,"package %s;",pg);
		
		l(sb,"using UnityEngine;");
		l(sb,"using System.Collections;");
		l(sb,"using System;");
		l(sb,"public abstract class %sClient {",clazz.getSimpleName());
		l(sb,"	protected BaseChannel channel;");
		l(sb,"	private int sn=0;");
		l(sb,"	public void dispath(RpcInput input){");
		l(sb,"		int cmd=input.readInt();");
		l(sb,"		switch(cmd){");
		for(RpcMethod m:methods){
			if(!m.getReturnType2().equals("void")){
				l(sb,"			case %s :{",m.getTag().cmd());
				l(sb,"				_%s(input,sn);",m.getM().getName());
				l(sb,"				return;");
				l(sb,"			}");
			}
		}
		l(sb,"		}");
		l(sb,"	}");
		//请求方法
		for(RpcMethod m:methods){
			if(m.getTransportMode()!=RPCTMode.ONLY_PUSH){
				l(sb,m.toClient4CSharp(module));
			}
		}
		//内部解析数据格式方法
		for(RpcMethod m:methods){
			if(m.getTransportMode()!=RPCTMode.ONLY_SEND){
				l(sb,m.toCSharpClientInner());
			}
		}
		for(RpcMethod m:methods){
			if(m.getTransportMode()!=RPCTMode.ONLY_SEND){
				l(sb,m.toCSharpClientAbstract());
			}
		}
		l(sb,"}");
		String path=pg.replaceAll("[.]","/");
		File file=new File("src/"+path);
		if(!file.exists()){
			file.mkdirs();
		}
		File clazzFile=new File("src/"+path+"/"+clazz.getSimpleName()+"Client.cs");
		if(!clazzFile.exists()){
			clazzFile.createNewFile();
		}
		FileOutputStream fis=new FileOutputStream(clazzFile);
		fis.write(sb.toString().getBytes("utf8"));
		fis.flush();
		fis.close();
		System.out.println(sb);
		
	}
	public static void clientJavaScript(String pg,List<RpcMethod> methods) throws Exception{
		StringBuffer sb=new StringBuffer();
		l(sb,"function Rpc(){}");
		l(sb,"var RpcService = new Rpc();");
		for(RpcMethod m:methods){
			l(sb,m.toJavaScript());
		}
		System.out.println(sb);
		String path=pg.replaceAll("[.]","/");
		File file=new File("src/"+path);
		if(!file.exists()){
			file.mkdirs();
		}
		File clazzFile=new File("src/"+path+"/RpcService.js");
		if(!clazzFile.exists()){
			clazzFile.createNewFile();
		}
		FileOutputStream fis=new FileOutputStream(clazzFile);
		fis.write(sb.toString().getBytes("utf8"));
		fis.flush();
		fis.close();
		
	}
	public static void l(StringBuffer sb,String line,Object... o ){
		
		sb.append(String.format(line, o)).append("\r\n");
	}
	public static void a(StringBuffer sb,String line,Object... o ){
		
		sb.append(String.format(line, o));
	}
	public static String toClassFile(Class c,String newPk){
		StringBuffer sb=new StringBuffer();
		l(sb,"package %s ;",newPk);
		l(sb,"import java.util.*;");
		l(sb,"import com.tongwan.common.io.rpc.*;");
		l(sb,"public class %s implements RpcVo{",c.getSimpleName());
		Field[] fields=c.getDeclaredFields();
		String generics = "";
		for(Field f:fields){
			if(f.getName().equals("this$0")){
				continue;
			}
			String t=f.getType().getSimpleName();
			Type genericType=f.getGenericType(); 
			if(genericType instanceof ParameterizedType){
				ParameterizedType type = (ParameterizedType) genericType;
			    Type[] typeArguments = type.getActualTypeArguments();
			    for(Type typeArgument : typeArguments){
			        Class typeArgClass = (Class) typeArgument;
			        generics= typeArgClass.getSimpleName();
			    }
			}
			if(generics!=null && !generics.equals("")){
				l(sb,"	public %s%s %s;",t,"<"+generics+">",f.getName());
			}else{
				l(sb,"	public %s %s;",t,f.getName());
			}
			
		}
		l(sb,"	public void writeTo(RpcOutput buffer){");
		for(Field f:fields){
			String simpleName=f.getType().getSimpleName();
			if(f.getName().equals("this$0")){
				continue;
			}
			String t=f.getType().getSimpleName();
			if(isInt(t)){
				l(sb,"		buffer.writeInt(%s);",f.getName());
			}else if(isLong(t)){
				l(sb,"		buffer.writeLong(%s);",f.getName());
			}else if(isString(t)){
				l(sb,"		buffer.writeString(%s);",f.getName());
			}else if(isBoolean(t)){
				l(sb,"		buffer.writeBoolean(%s);",f.getName());
			}else if(isDouble(t)){
				l(sb,"		buffer.writeDouble(%s);",f.getName());
			}else if(isList(t)){
				l(sb,"		buffer.writeList(%s);",f.getName());
			}else if(isIntArray(t)){
				l(sb,"		buffer.writeIntArray(%s);",f.getName());
			}else if(isObjectArray(t)){
				l(sb,"		buffer.writeObjectArray(%s);",f.getName());
			}else{
				l(sb,"		%s.writeTo(buffer);",f.getName());
			}
		}
		l(sb,"		");
		l(sb,"	}");
		l(sb,"	public void read(RpcInput in){");
		for(Field f:fields){
			String simpleName=f.getType().getSimpleName();
			if(f.getName().equals("this$0")){
				continue;
			}
			String t=f.getType().getSimpleName();
			if(isInt(t)){
				l(sb,"		%s=in.readInt();",f.getName());
			}else if(isLong(t)){
				l(sb,"		%s=in.readLong();",f.getName());
			}else if(isString(t)){
				l(sb,"		%s=in.readString();",f.getName());
			}else if(isBoolean(t)){
				l(sb,"		%s=in.readBoolean();",f.getName());
			}else if(isDouble(t)){
				l(sb,"		%s=in.readDouble();",f.getName());
			}else if(isIntArray(t)){
				l(sb,"		%s=in.readIntArray();",f.getName());
			}else if(isList(t)){
				l(sb,"		int size=in.readInt();");
				l(sb,"		%s=new ArrayList<>();",f.getName());
				l(sb,"		for(int i=0;i<size;i++){");
				if(isInt(t)){
					l(sb,"			%s.add(in.readInt());",f.getName());
				}else if(isLong(t)){
					l(sb,"			%s.add(in.readLong());",f.getName());
				}else if(isString(t)){
					l(sb,"			%s.add(in.readString());",f.getName());
				}else if(isBoolean(t)){
					l(sb,"			%s.add(in.readBoolean());",f.getName());
				}else if(isDouble(t)){
					l(sb,"			%s.add(in.readDouble());",f.getName());
				}else{
					l(sb,"			%s.add(in.readObject(%s.class));",f.getName(),generics);
				}
				l(sb,"		}");
			}else{
				l(sb,"		%s=in.readObject(%s.class);",f.getName(),t);
			}
		}
		l(sb,"	}");
		l(sb,"}");
		return sb.toString();
	}
	/**
	 * 
	 * @param c
	 * @param newPk
	 * @return
	 */
	public static String toClientClassFile(Class c,String newPk){
		StringBuffer sb=new StringBuffer();
		l(sb,"	public class %s : RpcVo{",c.getSimpleName());
		Field[] fields=c.getDeclaredFields();
		String generics = "";
		for(Field f:fields){
			if(f.getName().equals("this$0")){
				continue;
			}
			String t=f.getType().getSimpleName();
			Type genericType=f.getGenericType(); 
			if(genericType instanceof ParameterizedType){
				ParameterizedType type = (ParameterizedType) genericType;
			    Type[] typeArguments = type.getActualTypeArguments();
			    for(Type typeArgument : typeArguments){
			        Class typeArgClass = (Class) typeArgument;
			        generics= typeArgClass.getSimpleName();
			    }
			}
			if(generics!=null && !generics.equals("")){
				
				l(sb,"		public %s<%s> %s;",t,generics,f.getName());
			}else{
				if(t.equals("String")){
					t="string";
				}else if(isObjectArray(t)){
					t="object[]";
				}
				l(sb,"		public %s %s;",t,f.getName());
			}
			
		}
		l(sb,"		public void writeTo(RpcOutput buffer){");
		generics="";
		for(Field f:fields){
			Type genericType=f.getGenericType(); 
			if(genericType instanceof ParameterizedType){
				ParameterizedType type = (ParameterizedType) genericType;
			    Type[] typeArguments = type.getActualTypeArguments();
			    for(Type typeArgument : typeArguments){
			        Class typeArgClass = (Class) typeArgument;
			        generics= typeArgClass.getSimpleName();
			    }
			}
			String simpleName=f.getType().getSimpleName();
			if(f.getName().equals("this$0")){
				continue;
			}
			String t=f.getType().getSimpleName();
			if(isInt(t)){
				l(sb,"			buffer.writeInt(%s);",f.getName());
			}else if(isLong(t)){
				l(sb,"			buffer.writeLong(%s);",f.getName());
			}else if(isString(t)){
				l(sb,"			buffer.writeString(%s);",f.getName());
			}else if(isBoolean(t)){
				l(sb,"			buffer.writeBoolean(%s);",f.getName());
			}else if(isDouble(t)){
				l(sb,"			buffer.writeDouble(%s);",f.getName());
			}else if(isList(t)){
				l(sb,"			buffer.writeList(%s);",f.getName());
			}else if(isIntArray(t)){
				l(sb,"			buffer.writeIntArray(%s);",f.getName());
			}else if(isObjectArray(t)){
				//暂不支持
			}else{
				l(sb,"			%s.writeTo(buffer);",f.getName());
			}
		}
		l(sb,"		");
		l(sb,"		}");
		l(sb,"		public void read(RpcInput input){");
		for(Field f:fields){
			String simpleName=f.getType().getSimpleName();
			if(f.getName().equals("this$0")){
				continue;
			}
			String t=f.getType().getSimpleName();
			if(isInt(t)){
				l(sb,"			%s=input.readInt();",f.getName());
			}else if(isLong(t)){
				l(sb,"			%s=input.readLong();",f.getName());
			}else if(isString(t)){
				l(sb,"			%s=input.readString();",f.getName());
			}else if(isBoolean(t)){
				l(sb,"			%s=input.readBoolean();",f.getName());
			}else if(isDouble(t)){
				l(sb,"			%s=input.readDouble();",f.getName());
			}else if(isIntArray(t)){
				l(sb,"			%s=input.readIntArray();",f.getName());
			}else if(isObjectArray(t)){
				l(sb,"			%s=input.readObjectArray();",f.getName());
			}else if(isList(t)){
				l(sb,"			int size=input.readInt();");
				l(sb,"			%s=new List<%s>();",f.getName(),generics);
				l(sb,"			for(int i=0;i<size;i++){");
				if(isInt(t)){
					l(sb,"				%s.add(input.readInt());",f.getName());
				}else if(isLong(t)){
					l(sb,"				%s.add(input.readLong());",f.getName());
				}else if(isString(t)){
					l(sb,"				%s.add(input.readString());",f.getName());
				}else if(isBoolean(t)){
					l(sb,"				%s.add(input.readBoolean());",f.getName());
				}else if(isDouble(t)){
					l(sb,"				%s.add(input.readDouble());",f.getName());
				}else{
					l(sb,"				%s _%s = new %s();",generics,generics,generics);
					l(sb,"				_%s.read(input);",generics);
					l(sb,"				%s.Add(_%s);",f.getName(),generics);
					//l(sb,"				%s.add(input.readObject(%s.class));",f.getName(),generics);
				}
				l(sb,"			}");
			}else{
				l(sb,"			%s = new %s();",f.getName(),t);
				l(sb,"			%s.read(input);",f.getName());
				//l(sb,"			%s=input.readObject(%s.class);",f.getName(),t);
			}
		}
		l(sb,"		}");
		l(sb,"	}");
		return sb.toString();
	}
}
