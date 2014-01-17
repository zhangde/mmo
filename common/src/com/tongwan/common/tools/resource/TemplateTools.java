package com.tongwan.common.tools.resource;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tongwan.common.excel.ExcelX;
import com.tongwan.common.lang.TypeX;

/**
 * @author zhangde
 *
 * @date 2014年1月17日
 */
public class TemplateTools {
	static Log LOG = LogFactory.getLog(TemplateTools.class);
	public static List<Object> convertToTemplate(String fileName,String templatePackage){
		List<List<Object>> datas=ExcelX.readXml(fileName);
		String className=datas.remove(0).get(0).toString();//类名
		List<Object> types=datas.remove(0); //字段类型
		datas.remove(0);//描述字段
		List<Object> server=datas.remove(0); //服务端字段名
		List<Object> client=datas.remove(0); //客户端字段名
		types.remove(0);
		server.remove(0);
		client.remove(0);
			List<Object> result=new ArrayList<>();
			String fullName=templatePackage+"."+className;
			Class clazz=null;
			try {
				clazz = Class.forName(fullName);
			} catch (ClassNotFoundException e1) {
				LOG.error("模板类型配置错误"+fullName, e1);
				return null;
			}
			Field[] fields=clazz.getDeclaredFields();
			for(List<Object> data:datas){
				data.remove(0);
				Object item=null;
				try {
					item = clazz.newInstance();
				} catch (InstantiationException | IllegalAccessException e1) {
					LOG.error("模板类型配置错误"+fullName, e1);
					return null;
				}
				for(Field field:fields){
					field.setAccessible(true);
					int index=0;
					for(Object name:server){
						if(field.getName().equals(name.toString().trim())){
							try {
								field.set(item, TypeX.convertTo(data.get(index), types.get(index).toString()));
							} catch (IllegalArgumentException | IllegalAccessException e) {
								LOG.error(className+"字段["+field.getName()+"]设值错误",e);
							}
						}
						index++;
					}
					field.setAccessible(false);
				}
				if(item!=null){
					result.add(item);
				}
			}
			
			return result;
		
		
	}
}
