package com.tongwan.ai;

import com.tongwan.domain.monster.MonsterDomain;

/**
 * 怪物AI行为接口
 * @author zhangde
 *
 * @date 2014年1月18日
 */
public interface MonsterAction {
	/**
	 * 添加怪物
	 * @param actor
	 */
	public void addActor(MonsterDomain actor);
}
