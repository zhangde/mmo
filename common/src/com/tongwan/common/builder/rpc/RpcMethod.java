/**
 * 
 */
package com.tongwan.common.builder.rpc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import static com.tongwan.common.builder.rpc.ServiceG.l;
import static com.tongwan.common.builder.rpc.ServiceG.a;
import static com.tongwan.common.lang.TypeX.*;
/**
 * @author zhangde
 * @date 2013-9-25
 */
public class RpcMethod {
	private Method m;
	private RpcMethodTag tag;
	private Type[] parameterTypes;
	private Annotation[][] parameterAnnotations;
	public RpcMethod(Method m){
		this.m=m;
		tag= m.getAnnotation(RpcMethodTag.class);
		parameterTypes=m.getGenericParameterTypes();
		parameterAnnotations=m.getParameterAnnotations();
		System.out.println("");
	}
	public Method getM() {
		return m;
	}
	public RpcMethodTag getTag() {
		return tag;
	}
	public Type[] getParameterTypes() {
		return parameterTypes;
	}
	public String getReturnType(){
		Type returnType=m.getGenericReturnType();
		if(returnType instanceof ParameterizedType){
			ParameterizedType type = (ParameterizedType) returnType;
		    Type[] typeArguments = type.getActualTypeArguments();
		    for(Type typeArgument : typeArguments){
		        Class typeArgClass = (Class) typeArgument;
		        return "ResultObject<"+typeArgClass.getSimpleName()+">";
		    }
		}
		return "";
	}
	public String getReturnType2(){
		Type returnType=m.getGenericReturnType();
		if(returnType instanceof ParameterizedType){
			ParameterizedType type = (ParameterizedType) returnType;
		    Type[] typeArguments = type.getActualTypeArguments();
		    for(Type typeArgument : typeArguments){
		        Class typeArgClass = (Class) typeArgument;
		        return typeArgClass.getSimpleName();
		    }
		}
		return "";
	}
	public String toClient4Java(){
		StringBuffer sb=new StringBuffer();
		sb.append("	public  void ").append(m.getName()).append("(");
		for(int i=0;i<parameterTypes.length;i++){
			String t=parameterTypes[i].toString();
			t=t.substring(t.lastIndexOf(".")+1);
			if(t.indexOf("$")!=-1){
				t=t.substring(t.indexOf("$")+1,t.length());
			}
			String pName=tag.params()[i];
			sb.append(t).append(" ").append(pName).append(",");
		}
		if(parameterTypes.length>0){
			sb.delete(sb.length()-1, sb.length());
		}
		sb.append(") throws Exception{\r\n");
		l(sb,"		RpcOutput buffer=new RpcOutputNettyImpl();");
		l(sb,"		buffer.writeInt(%s);",getTag().cmd());
		l(sb,"		buffer.writeInt(sn++);");
		for(int i=0;i<parameterTypes.length;i++){
			String t=parameterTypes[i].toString();
			t=t.substring(t.lastIndexOf(".")+1);
			if(t.indexOf("$")!=-1){
				t=t.substring(t.indexOf("$")+1,t.length());
			}
			String pName=tag.params()[i];
//			sb.append(t).append(" ").append(pName).append(",");
			if(isInt(t)){
				l(sb,"		buffer.writeInt(%s);",pName);
			}else if(isLong(t)){
				l(sb,"		buffer.writeLong(%s);",pName);
			}else if(isString(t)){
				l(sb,"		buffer.writeString(%s);",pName);
			}else if(isBoolean(t)){
				l(sb,"		buffer.writeBoolean(%s);",pName);
			}else if(isDouble(t)){
				l(sb,"		buffer.writeDouble(%s);",pName);
			}
		}
		l(sb,"		channel.writeRpcOutput(buffer);");
		sb.append("	}");
		return sb.toString();
	}
	
	public String toClient4CSharp(int module){
		StringBuffer sb=new StringBuffer();
		sb.append("	public  void ").append(m.getName()).append("(");
		for(int i=0;i<parameterTypes.length;i++){
			String t=parameterTypes[i].toString();
			t=t.substring(t.lastIndexOf(".")+1);
			if(t.indexOf("$")!=-1){
				t=t.substring(t.indexOf("$")+1,t.length());
			}
			String pName=tag.params()[i];
			sb.append(t).append(" ").append(pName).append(",");
		}
		if(parameterTypes.length>0){
			sb.delete(sb.length()-1, sb.length());
		}
		sb.append("){\r\n");
		l(sb,"		RpcOutput buffer=new RpcOutput();");
		l(sb,"		buffer.writeInt(%s);//module",module);
		l(sb,"		buffer.writeInt(%s);//cmd",getTag().cmd());
		l(sb,"		buffer.writeInt(sn++);");
		for(int i=0;i<parameterTypes.length;i++){
			String t=parameterTypes[i].toString();
			t=t.substring(t.lastIndexOf(".")+1);
			if(t.indexOf("$")!=-1){
				t=t.substring(t.indexOf("$")+1,t.length());
			}
			String pName=tag.params()[i];
//			sb.append(t).append(" ").append(pName).append(",");
			if(isInt(t)){
				l(sb,"		buffer.writeInt(%s);",pName);
			}else if(isLong(t)){
				l(sb,"		buffer.writeLong(%s);",pName);
			}else if(isString(t)){
				l(sb,"		buffer.writeString(%s);",pName);
			}else if(isBoolean(t)){
				l(sb,"		buffer.writeBoolean(%s);",pName);
			}else if(isDouble(t)){
				l(sb,"		buffer.writeDouble(%s);",pName);
			}
		}
		l(sb,"		channel.writeRpcOutput(buffer);");
		sb.append("	}");
		return sb.toString();
	}
	
	public String toInner(){
		StringBuffer sb=new StringBuffer();
		sb.append("	public void _").append(m.getName()).append("(BaseChannel channel,RpcInput in,int sn) throws Exception{\r\n");
		for(int i=0;i<parameterAnnotations.length;i++){
			Annotation[] a=parameterAnnotations[i];
			if(a.length>0){
				continue;
			}
			String t=parameterTypes[i].toString();
			t=t.substring(t.lastIndexOf(".")+1);
			String pName=tag.params()[i];
			
			if(t.equals("int") || t.equals("Integer")){
				l(sb,"		%s %s=in.readInt();",t,pName);
			}else{
				l(sb,"		%s %s=in.readString();",t,pName);
			}
		}
		for(int i=0;i<parameterAnnotations.length;i++){
			Annotation[] a=parameterAnnotations[i];
			if(a.length>0){
				String t=parameterTypes[i].toString();
				t=t.substring(t.lastIndexOf(".")+1);
				if(t.indexOf("$")!=-1){
					t=t.substring(t.indexOf("$")+1,t.length());
				}
				String pName=tag.params()[i];
				sb.append("		").append(t).append(" ").append(pName).append("= new ").append(t).append("(").append(");").append("\r\n");
				
			}
		}
		
		//方法返回值类型
		a(sb,"		%s result=%s(",getReturnType(),m.getName());
		if(tag.params().length>0){
			for(String s:tag.params()){
				sb.append(s).append(",");
			}
			sb.delete(sb.length()-1, sb.length());
			
		}
		sb.append(");\r\n");
		l(sb,"		result.setCmd(%s);",getTag().cmd());
		l(sb,"		channel.writeResultObject(result);");
		sb.append("	}");
		return sb.toString();
	}
	public String toJavaClientInner(){
		String returnType=getReturnType2();
		StringBuffer sb=new StringBuffer();
		sb.append("	private void _").append(m.getName()).append("(RpcInput in,int sn) throws Exception{\r\n");
		l(sb,"		int state=in.readInt();");
		if(returnType.equals("byte[][]")){
			l(sb,"		byte[][] result=in.readByteArray2();");
			
		}else{
			l(sb,"		%s result=new %s();",returnType,returnType);
			l(sb,"		result.read(in);");
		}
		
		l(sb,"		%sCallback(state,result);",m.getName());
		sb.append("	}");
		return sb.toString();
	}
	public String toCSharpClientInner(){
		String returnType=getReturnType2();
		StringBuffer sb=new StringBuffer();
		sb.append("	private void _").append(m.getName()).append("(RpcInput input,int sn){\r\n");
		l(sb,"		int state=input.readInt();");
		if(returnType.equals("byte[][]")){
			l(sb,"		byte[][] result=input.readByteArray2();");
			
		}else{
			l(sb,"		Vos.%s result=new Vos.%s();",returnType,returnType);
			l(sb,"		result.read(input);");
		}
		
		l(sb,"		%sCallback(state,result);",m.getName());
		sb.append("	}");
		return sb.toString();
	}
	public String toJavaClientAbstract(){
		StringBuffer sb=new StringBuffer();
		l(sb,"	public abstract void %sCallback(int state,%s result)throws Exception;",m.getName(),getReturnType2());
		return sb.toString();
	}
	public String toCSharpClientAbstract(){
		StringBuffer sb=new StringBuffer();
		String type= getReturnType2();
		if(type.equals("byte[][]")){
			l(sb,"	public abstract void %sCallback(int state,%s result);",m.getName(),type);
		}else{
			l(sb,"	public abstract void %sCallback(int state,Vos.%s result);",m.getName(),type);
		}
		
		return sb.toString();
	}
	public String toAbstract(){
		StringBuffer sb=new StringBuffer();
		sb.append("	public abstract "+getReturnType()+" ").append(m.getName()).append("(");
		for(int i=0;i<parameterTypes.length;i++){
			String t=parameterTypes[i].toString();
			t=t.substring(t.lastIndexOf(".")+1);
			if(t.indexOf("$")!=-1){
				t=t.substring(t.indexOf("$")+1,t.length());
			}
			String pName=tag.params()[i];
			sb.append(t).append(" ").append(pName).append(",");
		}
		if(parameterTypes.length>0){
			sb.delete(sb.length()-1, sb.length());
		}
		sb.append(") throws Exception;");
		return sb.toString();
	}

	public String toJavaScript(){
		StringBuffer sb=new StringBuffer();
		StringBuffer pStr=new StringBuffer("");
		StringBuffer urlStr=new StringBuffer("");
		urlStr.append("+\"&cmd=").append(m.getName()).append("\"");
		if(parameterAnnotations.length>0){
			for(int i=0;i<parameterAnnotations.length;i++){
				Annotation[] a=parameterAnnotations[i];
				if(a.length>0){
					continue;
				}
				pStr.append(",").append(tag.params()[i]);
				urlStr.append("+\"&").append(tag.params()[i]).append("=\"+").append(tag.params()[i]);
			}
			pStr.delete(0, 1);
		}
		l(sb,"//%s",tag.remark());
		l(sb,"Rpc.prototype.%s=function(%s){",m.getName(),pStr.toString());
		l(sb,"	var url=server%s;",urlStr.toString());
		l(sb,"	$.getJSON(url,function(data){");
		l(sb,"		console.log(data);");
		l(sb,"		%sImpl(data);",m.getName());
		l(sb,"	});");
		l(sb,"}");
		return sb.toString();
	}
}
