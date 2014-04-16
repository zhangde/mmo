package com.tongwan.domain.monster;
import static com.tongwan.domain.attribute.AttributeKey.*;
import com.tongwan.domain.sprite.Battle;

/**
 * 怪物战斗模型
 * @author zhangde
 * @date 2014年1月4日
 */
public class MonsterBattle implements Battle{
	private int hp;
	private int hpMax;
	private int atk;
	
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
	
	public int getAtk() {
		return atk;
	}
	public void setAtk(int atk) {
		this.atk = atk;
	}
	
	public int getHpMax() {
		return hpMax;
	}
	public void setHpMax(int hpMax) {
		this.hpMax = hpMax;
	}
	@Override
	public int getAttribute(int key) {
		switch (key) {
			case HP: return hp;
			case ATK: return atk;
			case HP_MAX:return hpMax;
			default:
				break;
		}
		return 0;
	}
	
}
