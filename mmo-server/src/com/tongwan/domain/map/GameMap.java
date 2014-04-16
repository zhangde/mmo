package com.tongwan.domain.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import com.tongwan.common.path.AStar;
import com.tongwan.common.path.MaskTypes;
import com.tongwan.common.path.Point;
import com.tongwan.domain.sprite.Sprite;
import com.tongwan.type.SpriteType;


/**
 * 地图
 * @author zhangde
 * @date 2014年1月4日
 */
public class GameMap {
	private static final AtomicLong AUTO_GAMEMAP_ID=new AtomicLong();
	private static final Random RANDOM=new Random();
	private int id;
	private int column;
	private int rows;
	private byte[][] data;
	/** 地图上所有可见精灵*/
	private Set<Sprite>[] spires;
	public GameMap(byte[][] data){
		this.data=data;
		this.column=data.length;
		this.rows=data[0].length;
		spires=new Set[SpriteType.values().length];
		for(int i=0;i<spires.length;i++){
			spires[i]=Collections.synchronizedSet(new HashSet<Sprite>());
		}
	}
	/**
	 * 进入地图
	 * @param spire
	 */
	public void join(Sprite spire){
		Set<Sprite> typeSpire=spires[spire.getType().ordinal()];
		typeSpire.add(spire);
	}
	/**
	 * 退出地图
	 * @param spire
	 */
	public void quit(Sprite spire){
		Set<Sprite> typeSpire=spires[spire.getType().ordinal()];
		typeSpire.remove(spire);
	}
	/**
	 * 获得当前地图的所有怪物
	 * @return
	 */
	public Set<Sprite> getAllMonster(){
		return spires[SpriteType.MONSTER.ordinal()];
	}
	/**
	 * 是否在当前地图
	 * @param sprite
	 * @return
	 */
	public boolean inThisMap(Sprite sprite){
		if(id == sprite.getGameMap().getId()){
			return true;
		}
		return false;
	}
	/**
	 * 得到随机巡逻路径
	 * @param centerX
	 * @param centerY
	 * @param radius
	 * @return
	 */
	public List<Point> randomPoint(int centerX,int centerY,int radius){
		int beginX=0;
		int endX=0;
		int beginY=0;
		int endY=0;
		if(centerX-radius >0){
			beginX=centerX-radius;
		}
		if(centerX+radius >=column){
			endX=column;
		}else{
			endX=centerX+radius;
		}
		if(centerY-radius >0){
			beginY=centerY-radius;
		}
		if(centerY+radius >=rows){
			endY=rows;
		}else{
			endY=centerY+radius;
		}
		int tmpDistanceX=endX-beginX+1;
		int tmpDistanceY=endY-beginY+1;
		AStar aStar=new AStar(data, 100);
		while(true){
			int tmpX=beginX+RANDOM.nextInt(tmpDistanceX);
			int tmpY=beginY+RANDOM.nextInt(tmpDistanceY);
			long begin=System.currentTimeMillis();
			List<Point> path=aStar.find(centerY, centerX, tmpY, tmpX);
			long end=System.currentTimeMillis();
			//System.out.println("("+tmpX+","+tmpY+")"+"time:"+(end-begin));
			if(path!=null && !path.isEmpty()){
				
//				System.out.println("path:"+path.size());
				List<Point> result=new ArrayList<>();
				for(Point p:path){
//					System.out.println(p.x+","+p.y);
					result.add(new Point(p.getY(), p.getX()));
				}
				return result;
			}
			
		}
	}
	/**
	 * 当前坐标是否可通过
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isPathPass(int x,int y){
		return getMapMaskType(x, y)==MaskTypes.PATH_PASS;
	}
	/**
	 * 当前坐标掩码类型
	 * @param x
	 * @param y
	 * @return
	 */
	public byte getMapMaskType(int x,int y){
		if(x<0 || y<0 || x>=column || y>= rows){
			return MaskTypes.PATH_BARRIER;
		}
		return data[y][x];
	}
	/**
	 * @return the data
	 */
	public byte[][] getData() {
		return data;
	}
	public int getId() {
		return id;
	}
	
	
	
}
