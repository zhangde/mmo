package com.tongwan.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.tongwan.common.lang.PackageScanner;
import com.tongwan.common.tools.resource.Template;
import com.tongwan.common.tools.resource.TemplateTools;
import com.tongwan.context.event.ResourceLoadedEvent;
import com.tongwan.domain.configure.Storage;
import com.tongwan.service.ResourceService;

/**
 * @author zhangde
 *
 * @date 2014年1月17日
 */
@Service
public class ResourceServiceImpl implements ResourceService,ApplicationContextAware,ApplicationListener<ContextRefreshedEvent>{

	private ApplicationContext context;
	private String templatePath="com.tongwan.template";
	private String templateFilePath="resource/template/";
	/**
	 * 所有静态配置数据
	 */
	private Map<Class<?>,Storage> storages=new HashMap<>();
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Set<Class<?>> classes=PackageScanner.getClasses(templatePath);
		for(Class clazz:classes){
			List<Template> list=TemplateTools.convertToTemplate(clazz, templateFilePath);
			Storage storage = new Storage(clazz,list);
			storages.put(clazz, storage);
		}
		context.publishEvent(new ResourceLoadedEvent(event));
	}


	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context=context;
	}


	@Override
	public List list(Class clazz) {
		Storage storage = storages.get(clazz);
		return storage.list();
	}


	@Override
	public Object get(Class clazz, int id) {
		Storage storage = storages.get(clazz);
		return storage.get(id);
	}

}
