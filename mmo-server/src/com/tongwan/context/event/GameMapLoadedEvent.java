package com.tongwan.context.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;

/**
 * 地图数据初始化完成事件
 * @author zhangde
 *
 * @date 2014年1月18日
 */
public class GameMapLoadedEvent extends ApplicationContextEvent{
	public GameMapLoadedEvent(ApplicationContext source) {
		super(source);
	}
}
