package com.tongwan.module.monster.domain;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tongwan.common.ai.behaviortree.BehaviorActor;
import com.tongwan.common.ai.behaviortree.BehaviorTree;
import com.tongwan.common.path.Point;
import com.tongwan.module.map.domain.GameMap;
import com.tongwan.net.TcpHandler;

/**
 * @author zhangde
 * @date 2013年12月25日
 */
public class MonsterDomain implements BehaviorActor{
	private static Log LOG = LogFactory.getLog(MonsterDomain.class);
	private static final AtomicLong AUTO_MONSTER_ID=new AtomicLong();
	/** 唯一标识 */
	private long id;
	/** 基本配置ID */
	private int baseId;
	/** 战斗数据模型 */
	private MonsterBattle battle;
	/** 所在地图 */
	private GameMap gameMap;
	/** 当前怪物的AI行为树 */
	private BehaviorTree behaviorTree;
	/** 当前x坐标 */
	private int x;
	/** 当前y坐标 */
	private int y;
	/** 出生的x坐标*/
	private int homeX;
	/** 出生的y坐标*/
	private int homeY;
	/** 巡逻半径 */
	private int patrolRadius;
	/** 最后一次移动时间*/
	private long lastMoveTime;
	/** 是否正在移动 */
	private boolean isMoving;
	/** 当前移动路径 */
	private List<Point> currentPath;
	public MonsterDomain(int baseId,MonsterBattle battle,int homeX,int homeY,int patrolRadius,BehaviorTree behaviorTree,GameMap gameMap){
		this.id=AUTO_MONSTER_ID.getAndIncrement();
		this.baseId=baseId;
		this.battle=battle;
		this.behaviorTree=behaviorTree;
		this.homeX=homeX;
		this.homeY=homeY;
		this.x=homeX;
		this.y=homeY;
		this.patrolRadius=patrolRadius;
		this.gameMap=gameMap;
	}
	/**
	 * 是否死亡
	 * @return
	 */
	private boolean isDead(){
		return battle.isDead();
	}
	/**
	 * 是否到达复活时间
	 * @return
	 */
	private boolean isTimeToResurrection(){
		return true;
	}
	/**
	 * 巡逻
	 * @return
	 */
	private boolean patrol(){
		if(currentPath==null || currentPath.isEmpty()){
			if(System.currentTimeMillis() - lastMoveTime >= 00){
				currentPath=gameMap.randomPoint(x, y, patrolRadius);
				if(currentPath!=null && !currentPath.isEmpty()){
					lastMoveTime=System.currentTimeMillis();
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 跟据当前路径走一步
	 * @return
	 */
	private boolean moveOneStep(){
		if(currentPath!=null && !currentPath.isEmpty()){
			if(System.currentTimeMillis() - lastMoveTime >= 200){//200毫秒移动一步
				Point point=currentPath.remove(0);
				if(gameMap.isPathPass(point.x, point.y)){
					this.x=point.x;
					this.y=point.y;
					LOG.debug("["+this.id+"]移动到:("+x+","+y+")");
					TcpHandler.pushAddSprite(this);
					return true;
				}else{
					currentPath=null;
				}
			}
		}
		return false;
	}
	private boolean resurrection(){
		return true;
	}
	
	
	
	@Override
	public boolean runAi(){
		moveOneStep();
		return behaviorTree.run(this);
	}
	private static Map<String,Method> methods=new HashMap<>();
	@Override
	public boolean executeAction(String actionType) {
		try{
			//LOG.debug("action:"+actionType);
			Method method=methods.get(actionType);
			if(method==null){
				method=this.getClass().getDeclaredMethod(actionType);
				method.setAccessible(true);
				methods.put(actionType, method);
			}
			Object result=method.invoke(this);
			return (boolean) result;
		}catch(Exception e){
			LOG.error("AI执行方法调用错误",e);
		}
		return false;
	}

	@Override
	public boolean executeDetermine(String conditionType) {
		try{
			Method method=methods.get(conditionType);
			if(method==null){
				method=this.getClass().getDeclaredMethod(conditionType);
				method.setAccessible(true);
				methods.put(conditionType, method);
			}
			Object result=method.invoke(this);
			return (boolean) result;
		}catch(Exception e){
			LOG.error("AI执行方法调用错误",e);
		}
		return false;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

}
