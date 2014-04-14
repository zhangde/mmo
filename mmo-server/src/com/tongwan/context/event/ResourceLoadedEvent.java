package com.tongwan.context.event;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ApplicationContextEvent;

/**
 * 静态资源数据加载完成事件
 * @author zhangde
 *
 * @date 2014年1月18日
 */
public class ResourceLoadedEvent extends ApplicationEvent{
	public ResourceLoadedEvent(Object source) {
		super(source);
	}
}
