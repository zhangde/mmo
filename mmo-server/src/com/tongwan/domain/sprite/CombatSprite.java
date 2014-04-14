package com.tongwan.domain.sprite;

import com.tongwan.domain.map.GameMap;

/**
 * 所有在地图可战斗的对象的父类
 * @author zhangde
 * @date 2014年4月14日
 */
public abstract class CombatSprite extends BaseSprite{
	/**
	 * 战斗对象
	 */
	private Battle battle;
	public CombatSprite(long id, GameMap gameMap, int x, int y,Battle battle) {
		super(id, gameMap, x, y);
		this.battle=battle;
	}
	public Battle getBattle() {
		return battle;
	}

	
}
