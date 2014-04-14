package com.tongwan.domain.configure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tongwan.common.tools.resource.Template;

/**
 * 静态数据存储
 * @author zhangde
 * @date 2014年1月17日
 * @param <T>
 */
public class Storage<T> {
	private static final Log LOG=LogFactory.getLog(Storage.class);
	private Class<T> clazz;
	private Map<Integer,T> data=new HashMap<>();
	private List<T> dataList=new ArrayList<>();
	public Storage(Class clazz ,List<Template> templates){
		this.clazz=clazz;
		for(Template template :templates){
			T t=(T)template;
			dataList.add(t);
			T exist= data.put(template.getId(), t);
			if(exist!=null){
				LOG.error(template.getClass().getSimpleName()+" id " + template.getId() +" 重复 ");
			}
		}
		
	}
	public T get(int id){
		return data.get(id);
	}
	public  List<T> list(){
		return dataList;
	}
}
