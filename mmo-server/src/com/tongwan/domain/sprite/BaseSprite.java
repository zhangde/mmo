package com.tongwan.domain.sprite;

import com.tongwan.domain.map.GameMap;
/**
 * 基础抽象精灵，所有在地图可显示对象的父类
 * @author zhangde
 * @date 2014年4月14日
 */
public abstract class BaseSprite implements Sprite{

	/** 唯一标识 */
	private long id;
	/** 所在地图 */
	private GameMap gameMap;
	/** 当前x坐标 */
	private int x;
	/** 当前y坐标 */
	private int y;
	
	public BaseSprite(long id,GameMap gameMap,int x,int y){
		this.id=id;
		this.gameMap=gameMap;
		this.x=x;
		this.y=y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public long getId() {
		return id;
	}
	public GameMap getGameMap() {
		return gameMap;
	}
	
	
}
