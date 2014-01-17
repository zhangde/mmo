package com.tongwan.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.tongwan.common.lang.PackageScanner;
import com.tongwan.common.tools.resource.TemplateTools;
import com.tongwan.context.event.ResourceLoadedEvent;
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
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Set<Class<?>> classes=PackageScanner.getClasses(templatePath);
		for(Class clazz:classes){
			List list=TemplateTools.convertToTemplate(clazz, templateFilePath);
			System.out.println(list.size());
		}
		context.publishEvent(new ResourceLoadedEvent(context));
	}


	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context=context;
	}

}
