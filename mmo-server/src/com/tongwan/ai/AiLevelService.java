package com.tongwan.ai;

import com.tongwan.common.ai.behaviortree.BehaviorTree;

/**
 * AI等级接口
 * @author zhangde
 *
 * @date 2014年1月18日
 */
public interface AiLevelService {
	public BehaviorTree get(int level);
}
