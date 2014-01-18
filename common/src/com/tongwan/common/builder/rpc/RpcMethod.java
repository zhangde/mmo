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
	public String toInner(){
		StringBuffer sb=new StringBuffer();
		sb.append("	public void _").append(m.getName()).append("(BaseChannel channel,int sn) throws Exception{\r\n");
		for(int i=0;i<parameterAnnotations.length;i++){
			Annotation[] a=parameterAnnotations[i];
			if(a.length>0){
				continue;
			}
			String t=parameterTypes[i].toString();
			t=t.substring(t.lastIndexOf(".")+1);
			String pName=tag.params()[i];
			
			if(t.equals("int") || t.equals("Integer")){
				l(sb,"		%s %s=channel.readInt();",t,pName);
			}else{
				l(sb,"		%s %s=channel.readString();",t,pName);
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
			sb.append(");\r\n");
		}
		l(sb,"		channel.writeResultObject(result);");
//		sb.append("		Map returnMap = new HashMap();\r\n");
//		for(int i=0;i<parameterAnnotations.length;i++){
//			Annotation[] a=parameterAnnotations[i];
//			if(a.length>0){
//				String t=parameterTypes[i].toString();
//				t=t.substring(t.lastIndexOf(".")+1);
//				String pName=tag.params()[i];
//				if(parameterTypes[i].equals(HashMap.class) || parameterTypes[i].equals(ArrayList.class)){
//					sb.append("		returnMap.put(\"").append(pName).append("\","+pName+");").append("\r\n");
//				}else{
//					sb.append("		returnMap.put(\"").append(pName).append("\","+pName+".toMap());").append("\r\n");
//				}
//				
//				
//			}
//		}
//		sb.append("		writeJson(channel,returnMap,callback);\r\n");
		sb.append("	}");
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
		sb.delete(sb.length()-1, sb.length());
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
