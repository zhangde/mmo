package com.tongwan.template;

import com.tongwan.common.tools.resource.Template;

/**
 * 怪物模板数据
 * @author zhangde
 *
 * @date 2014年1月11日
 */
public class MonsterTemplate implements Template{
	private int id;
	private String name;
	private int level;
	private int maxHp;
	private int atk;
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public int getLevel() {
		return level;
	}
	public int getMaxHp() {
		return maxHp;
	}
	public int getAtk() {
		return atk;
	}
	
}
