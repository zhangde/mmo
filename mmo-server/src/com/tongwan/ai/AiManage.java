package com.tongwan.ai;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.tongwan.common.thread.NameThreadFactory;

/**
 * @author zhangde
 * @date 2013年12月26日
 */
public class AiManage {
	/**管理的所有AI行为对象*/
	private Set<BehaviorActor> actors=new CopyOnWriteArraySet<>();
	/**AI行为处理线程池*/
	private ThreadPoolExecutor poolExecutor;
	/**AI调度线程*/
	private AiDispatchThread aiDispatchThread;
	
	public void init(){
		poolExecutor=new ThreadPoolExecutor(10, 10, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(),new NameThreadFactory("AI执行线程组"));
		aiDispatchThread=new AiDispatchThread(actors, poolExecutor);
		aiDispatchThread.start();
	}
	public void addActor(BehaviorActor actor){
		actors.add(actor);
	}
}
