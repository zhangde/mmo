package com.tongwan.domain.sprite;
/**
 * 所有战斗对象的父级接口
 * @author zhangde
 * @date 2014年4月14日
 */
public interface Battle {
	public int getAttribute(int key);
	public boolean isDead();
}
