package com.tongwan.domain.monster;
/**
 * 怪物战斗模型
 * @author zhangde
 * @date 2014年1月4日
 */
public class MonsterBattle {
	private int hp;
	public boolean isDead(){
		return hp<=0;
	}
	/**
	 * @return the hp
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * @param hp the hp to set
	 */
	public void setHp(int hp) {
		this.hp = hp;
	}
	
}
