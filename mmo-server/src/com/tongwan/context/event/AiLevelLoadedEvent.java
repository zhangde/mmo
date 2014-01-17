package com.tongwan.context.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;

/**
 * AI脚本加载完成事件
 * @author zhangde
 *
 * @date 2014年1月18日
 */
public class AiLevelLoadedEvent extends ApplicationContextEvent{
	public AiLevelLoadedEvent(ApplicationContext source) {
		super(source);
	}
}
