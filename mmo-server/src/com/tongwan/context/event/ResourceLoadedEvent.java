package com.tongwan.context.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;

/**
 * 静态资源数据加载完成事件
 * @author zhangde
 *
 * @date 2014年1月18日
 */
public class ResourceLoadedEvent extends ApplicationContextEvent{
	public ResourceLoadedEvent(ApplicationContext source) {
		super(source);
	}
}
