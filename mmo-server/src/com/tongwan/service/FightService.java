package com.tongwan.service;

import com.tongwan.domain.monster.MonsterDomain;

/**
 * 战斗接口
 * @author zhangde
 * @date 2014年1月11日
 */
public interface FightService {
	/**
	 * 怪物攻击怪物
	 * @param attacker
	 * @param target
	 */
	public void monster2Monster(MonsterDomain attacker,MonsterDomain target);
}
