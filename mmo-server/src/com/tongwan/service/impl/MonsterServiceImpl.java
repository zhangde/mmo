package com.tongwan.service.impl;
import static com.tongwan.domain.attribute.AttributeKey.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.tongwan.ai.AiLevelService;
import com.tongwan.ai.MonsterAction;
import com.tongwan.context.event.AiLevelLoadedEvent;
import com.tongwan.context.event.ResourceLoadedEvent;
import com.tongwan.domain.map.GameMap;
import com.tongwan.domain.monster.MonsterBattle;
import com.tongwan.domain.monster.MonsterDomain;
import com.tongwan.domain.sprite.Battle;
import com.tongwan.service.GameMapService;
import com.tongwan.service.MonsterService;
import com.tongwan.service.ResourceService;
import com.tongwan.template.MonsterTemplate;
/**
 * @author zhangde
 * 
 * @date 2014年1月17日
 */
@Service
public class MonsterServiceImpl implements MonsterService,ApplicationListener<ApplicationEvent> {

	@Autowired
	private GameMapService gameMapService;
	@Autowired
	private MonsterAction monsterAction;
	@Autowired
	private AiLevelService aiLevelService;
	@Autowired
	private ResourceService resourceService;
	/**
	 * 初始化怪物数据
	 */
	private void initMonster() {
		GameMap gameMap=gameMapService.getGameMap(1);
		List<MonsterTemplate> monsterTemplates = resourceService.list(MonsterTemplate.class);
		for(MonsterTemplate template:monsterTemplates){
			MonsterBattle battle = new MonsterBattle();
			battle.setHp(template.getMaxHp());
			battle.setAtk(template.getAtk());
			MonsterDomain monster = new MonsterDomain(template, battle, 0, 0, 10, aiLevelService.get(1), gameMap);
			gameMap.join(monster);
			monsterAction.addActor(monster);
		}
//		for (int i = 1; i < 2; i++) {
//			MonsterBattle battle = new MonsterBattle();
//			battle.setHp(100);
//			MonsterDomain monster = new MonsterDomain(i, battle, 0, 0, 10, aiLevelService.get(1),map1);
//			System.out.println("创建monster [" + i + "]");
//			map1.join(monster);
//			monsterAction.addActor(monster);
//			
//		}
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if(event.getClass().equals(AiLevelLoadedEvent.class)){
			initMonster();
		}
		
	}
	
	@Override
	public Object[] attributes(MonsterDomain monsterDomain, int[] keys) {
		Battle battle = monsterDomain.getBattle();
		MonsterTemplate template = monsterDomain.getMonsterTemplate();
		Object[] values=new Object[keys.length];
		for(int index=0;index<keys.length;index++){
			int key=keys[index];
			switch (key) {
			case HP:
				values[index] = battle.getAttribute(key);
				break;
			case NAME:
				values[index] = template.getName();
				break;
			default:
				break;
			}
		}
		
		return values;
	}
}
