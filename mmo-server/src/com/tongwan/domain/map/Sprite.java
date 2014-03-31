package com.tongwan.domain.map;

/**
 * 地图可见精灵抽象接口
 * @author zhangde
 *
 * @date 2014年1月17日
 */
public interface Sprite {
	public long getId();
	public int getX();
	public int getY();
	public GameMap getGameMap();
	public SpireType getType();
}
