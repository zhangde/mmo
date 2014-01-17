package com.tongwan.ai.impl;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.tongwan.ai.AiLevelService;
import com.tongwan.common.ai.behaviortree.BehaviorTree;
import com.tongwan.common.ai.behaviortree.BehaviorTreeContext;
import com.tongwan.common.io.FileX;
import com.tongwan.context.event.AiLevelLoadedEvent;
import com.tongwan.context.event.GameMapLoadedEvent;
import com.tongwan.context.event.ResourceLoadedEvent;

/**
 * @author zhangde
 *
 * @date 2014年1月18日
 */
@Component
public class AiLevelServiceImpl implements AiLevelService,ApplicationContextAware,ApplicationListener<GameMapLoadedEvent>{
	static Log LOG=LogFactory.getLog(AiLevelServiceImpl.class);
	private ApplicationContext context;
	private BehaviorTreeContext behaviorTreeContext;
	private String aiScriptPath="resource/monster.ai";
	@Override
	public BehaviorTree get(int level) {
		for(BehaviorTree tree:behaviorTreeContext.getTrees()){
			if(tree.getId()==level){
				return tree;
			}
		}
		return null;
	}
	@Override
	public void onApplicationEvent(GameMapLoadedEvent event) {
		try {
			String script=FileX.readAll(aiScriptPath);
			behaviorTreeContext=new BehaviorTreeContext();
			behaviorTreeContext.load(script);
			context.publishEvent(new AiLevelLoadedEvent(context));
		} catch (IOException e) {
			LOG.error("加载AI脚本错误",e);
		}
		
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context=applicationContext;
	}

}
