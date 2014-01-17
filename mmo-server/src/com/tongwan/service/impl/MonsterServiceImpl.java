package com.tongwan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.tongwan.ai.AiLevelService;
import com.tongwan.ai.MonsterAction;
import com.tongwan.context.event.AiLevelLoadedEvent;
import com.tongwan.context.event.GameMapLoadedEvent;
import com.tongwan.domain.map.GameMap;
import com.tongwan.domain.monster.MonsterBattle;
import com.tongwan.domain.monster.MonsterDomain;
import com.tongwan.service.GameMapService;
import com.tongwan.service.MonsterService;

/**
 * @author zhangde
 * 
 * @date 2014年1月17日
 */
@Service
public class MonsterServiceImpl implements MonsterService,ApplicationListener<AiLevelLoadedEvent> {
	@Autowired
	private GameMapService gameMapService;
	@Autowired
	private MonsterAction monsterAction;
	@Autowired
	private AiLevelService aiLevelService;
	/**
	 * 初始化怪物数据
	 */
	private void initMonster() {
		GameMap map1=gameMapService.getGameMap(1);
		for (int i = 1; i < 100; i++) {
			MonsterBattle battle = new MonsterBattle();
			battle.setHp(100);
			MonsterDomain monster = new MonsterDomain(i, battle, 0, 0, 5, aiLevelService.get(1),map1);
			System.out.println("创建monster [" + i + "]");
			monsterAction.addActor(monster);
		}
	}

	@Override
	public void onApplicationEvent(AiLevelLoadedEvent event) {
		initMonster();
	}
}
