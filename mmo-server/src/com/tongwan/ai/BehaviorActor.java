package com.tongwan.ai;


/**
 * 行为角色接口
 * @author zhangde
 * @date 2013年12月24日
 */
public interface BehaviorActor {
	/**
	 * 执行AI角色动作
	 * @param actionType
	 */
	public boolean executeAction(String actionType);
	/**
	 * 执行AI角色的判定
	 */
	public boolean executeDetermine(String conditionType);
	/**
	 * 运行自身AI行为
	 */
	public boolean runAi();
}
