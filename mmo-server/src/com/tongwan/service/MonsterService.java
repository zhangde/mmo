package com.tongwan.service;

import com.tongwan.domain.monster.MonsterDomain;

/**
 * 怪物处理接口
 * @author zhangde
 *
 * @date 2014年1月17日
 */
public interface MonsterService {
	/**
	 * 查询怪物的属性
	 * @param monsterDomain 怪物领域对象
	 * @param keys 属性列表
	 * @return
	 */
	public Object[] attributes(MonsterDomain monsterDomain,int[] keys);
}
