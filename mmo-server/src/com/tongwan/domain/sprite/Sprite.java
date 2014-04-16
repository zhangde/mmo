package com.tongwan.domain.sprite;

import com.tongwan.domain.map.GameMap;
import com.tongwan.type.SpriteType;

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
	public SpriteType getType();
}
