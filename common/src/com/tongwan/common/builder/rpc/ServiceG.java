/**
 * 
 */
package com.tongwan.common.builder.rpc;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tongwan.common.net.ResultObject;

import static com.tongwan.common.lang.TypeX.*;

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
		@RpcMethodTag(cmd=1,params={"name","password"},remark="登陆")
		public ResultObject<UserVO> login(String name,String password);
		@RpcMethodTag(cmd=2,params={"name"},remark="封号")
		public ResultObject<UserVO> closeUser(String name);
	}
	
	public static void main(String[] args) throws Exception {
		serverDataStruct("gen.data",ServiceG.class);
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
		server("gen.service",methods);
		
		clientJavaScript("gen", methods);
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
	public static void server(String pg,List<RpcMethod> methods) throws Exception{
		StringBuffer sb=new StringBuffer();
		l(sb,"package %s;",pg);
		
		l(sb,"import gen.data.*;");
		l(sb,"import com.tongwan.common.net.ResultObject;");
		l(sb,"import com.tongwan.common.net.channel.BaseChannel;");
		l(sb,"public abstract class RpcService {");
		l(sb,"	public void process(BaseChannel channel) throws Exception{");
//		l(sb,"		Map parame=channel.getParame();");
		l(sb,"		int cmd=channel.readInt(); //指令编号");
		l(sb,"		int sn=channel.readInt();  //指令序号");
		l(sb,"		switch(cmd){");
		for(RpcMethod m:methods){
			l(sb,"			case %s :{",m.getTag().cmd());
			l(sb,"				_%s(channel,sn);",m.getM().getName());
			l(sb,"				return;");
			l(sb,"			}");
		}
		l(sb,"		}");
		l(sb,"		throw new RuntimeException(\" cmd: \" + cmd + \" not found processor.\");");
		l(sb,"	}");
		for(RpcMethod m:methods){
			l(sb,m.toInner());
		}
		for(RpcMethod m:methods){
			l(sb,m.toAbstract());
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
		File clazzFile=new File("src/"+path+"/RpcService.java");
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
		l(sb,"import com.tongwan.common.builder.rpc.*;");
		l(sb,"import com.tongwan.common.builder.rpc.io.*;");
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
		l(sb,"	public void writeTo(RpcOut buffer){");
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
			}else if(isArray(t)){
				l(sb,"		buffer.writeArray(%s);",f.getName());
			}else{
				l(sb,"		%s.writeTo(buffer);",f.getName());
			}
		}
		l(sb,"		");
		l(sb,"	}");
		l(sb,"	public void read(RpcIn in){");
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
			}else if(isArray(t)){
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
}
