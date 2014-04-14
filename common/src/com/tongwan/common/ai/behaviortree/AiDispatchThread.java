package com.tongwan.common.ai.behaviortree;

import java.util.Collection;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * AI调度线程
 * @author zhangde
 * @date 2013年12月27日
 */
public class AiDispatchThread extends Thread{
	private static Log LOG=LogFactory.getLog(AiDispatchThread.class);
	/**管理的AI行为对象*/
	private Collection<BehaviorActor> actors;
	/**AI行为处理线程池*/
	private ThreadPoolExecutor poolExecutor;
	public AiDispatchThread(Collection<BehaviorActor> actors,ThreadPoolExecutor poolExecutor){
		setName("AI调度线程");
		setDaemon(false);
		this.actors=actors;
		this.poolExecutor=poolExecutor;
	}
	@Override
	public void run() {
		while(true){
			if(actors!=null && !actors.isEmpty()){
				for(BehaviorActor actor:actors){
					poolExecutor.submit(new AiRun(actor));
				}
			}
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
