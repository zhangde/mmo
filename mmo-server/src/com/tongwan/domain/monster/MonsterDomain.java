package com.tongwan.domain.monster;

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
import com.tongwan.domain.map.GameMap;
import com.tongwan.domain.sprite.BaseSprite;
import com.tongwan.domain.sprite.CombatSprite;
import com.tongwan.domain.sprite.SpireType;
import com.tongwan.domain.sprite.Sprite;
import com.tongwan.helper.FightHelper;
import com.tongwan.helper.WorldPushHelper;
import com.tongwan.net.TcpHandler;
import com.tongwan.template.MonsterTemplate;

/**
 * @author zhangde
 * @date 2013年12月25日
 */
public class MonsterDomain extends CombatSprite implements BehaviorActor{
	private static Log LOG = LogFactory.getLog(MonsterDomain.class);
	private static final AtomicLong AUTO_MONSTER_ID=new AtomicLong();
	/** 基本配置 */
	private MonsterTemplate monsterTemplate;
	/** 当前怪物的AI行为树 */
	private BehaviorTree behaviorTree;
	
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
	private List<Point> currentPath=null;
	/** 当前攻击目标 */
	private CombatSprite attackTarget=null;
	public MonsterDomain(MonsterTemplate template,MonsterBattle battle,int homeX,int homeY,int patrolRadius,BehaviorTree behaviorTree,GameMap gameMap){
		super(AUTO_MONSTER_ID.getAndIncrement(),gameMap,homeX,homeY,battle);
		this.monsterTemplate=template;
		this.behaviorTree=behaviorTree;
		this.homeX=homeX;
		this.homeY=homeY;
		this.patrolRadius=patrolRadius;
	}
	/**
	 * 是否死亡
	 * @return
	 */
	private boolean isDead(){
		return getBattle().isDead();
	}
	/**
	 * 是否到达复活时间
	 * @return
	 */
	private boolean isTimeToResurrection(){
		return true;
	}
	/**
	 * 寻找敌人
	 * @return
	 */
	private boolean findTheEnemy(){
		boolean existAttackTarget = attackTarget!=null;//是否存在攻击目标
		boolean targetAlive=!attackTarget.getBattle().isDead();//攻击目标是否活着
		boolean targetInThisMap=getGameMap().inThisMap(attackTarget);//攻击目标是否在当前地图
		if(existAttackTarget && targetAlive && targetInThisMap){
			attackTarget=null;
			return false;
		}
		
		return true;
	}
	/**
	 * 巡逻
	 * @return
	 */
	private boolean patrol(){
		if(currentPath==null || currentPath.isEmpty()){
			if(System.currentTimeMillis() - lastMoveTime >= 10000){
				currentPath=getGameMap().randomPoint(getX(), getY(), patrolRadius);
				if(currentPath!=null && !currentPath.isEmpty()){
					//lastMoveTime=System.currentTimeMillis();
					LOG.debug("["+getId()+"]开始移动");
					WorldPushHelper.pushMotion(this, currentPath);
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
		long now=System.currentTimeMillis();
		if(currentPath!=null && !currentPath.isEmpty()){
			if(now - lastMoveTime >= 250){//200毫秒移动一步
				Point point=currentPath.remove(0);
				if(getGameMap().isPathPass(point.x, point.y)){
					lastMoveTime=now;
					setX(point.x);
					setY(point.y);
					LOG.debug("["+getId()+"]移动到:("+getX()+","+getY()+")"+now);
					//TcpHandler.pushAddSprite(this);
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
	private boolean fight(){
		FightHelper.fight(this, this);
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

	public MonsterBattle getMonsterBattle() {
		return (MonsterBattle) getBattle();
	}
	
	public MonsterTemplate getMonsterTemplate() {
		return monsterTemplate;
	}
	@Override
	public SpireType getType() {
		return SpireType.MONSTER;
	}
	
}
