package com.tongwan.ai.impl;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.tongwan.ai.MonsterAction;
import com.tongwan.common.ai.behaviortree.AiDispatchThread;
import com.tongwan.common.ai.behaviortree.BehaviorActor;
import com.tongwan.common.thread.NameThreadFactory;
import com.tongwan.domain.monster.MonsterDomain;

/**
 * @author zhangde
 *
 * @date 2014年1月18日
 */
@Component
public class MonsterActionImpl implements MonsterAction {
	/**管理的所有AI行为对象*/
	private Set<BehaviorActor> actors=new CopyOnWriteArraySet<>();
	/**AI行为处理线程池*/
	private ThreadPoolExecutor poolExecutor;
	/**AI调度线程*/
	private AiDispatchThread aiDispatchThread;
	@PostConstruct
	public void init(){
		poolExecutor=new ThreadPoolExecutor(10, 10, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(),new NameThreadFactory("AI执行线程组"));
		aiDispatchThread=new AiDispatchThread(actors, poolExecutor);
		aiDispatchThread.start();
	}
	@Override
	public void addActor(MonsterDomain actor) {
		actors.add(actor);
	}

}
