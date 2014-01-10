package com.tongwan.module.map.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.tongwan.common.path.AStar;
import com.tongwan.common.path.MaskTypes;
import com.tongwan.common.path.Point;


/**
 * 地图
 * @author zhangde
 * @date 2014年1月4日
 */
public class GameMap {
	private static final Random RANDOM=new Random();
	private int column;
	private int rows;
	private byte[][] data;
	public GameMap(byte[][] data){
		this.data=data;
		this.column=data.length;
		this.rows=data[0].length;
		
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
			System.out.println("("+tmpX+","+tmpY+")"+"time:"+(end-begin));
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
	
	
	
}
