package com.tongwan.common.ai.behaviortree;

import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author zhangde
 * @date 2013年12月26日
 */
public class AiRun implements Callable<Boolean>{
	private static Log LOG=LogFactory.getLog(AiRun.class);
	private BehaviorActor actor;
	public AiRun(BehaviorActor actor){
		this.actor=actor;
	}
	@Override
	public Boolean call() throws Exception {
		boolean result=false;
		try{
			result=actor.runAi();
		}catch(Exception e){
			e.printStackTrace();
			LOG.error("AI动作执行异常",e);
		}
		return result;
	}

}
