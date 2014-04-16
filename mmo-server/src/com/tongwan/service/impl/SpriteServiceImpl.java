package com.tongwan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tongwan.domain.monster.MonsterDomain;
import com.tongwan.domain.sprite.Sprite;
import com.tongwan.service.MonsterService;
import com.tongwan.service.SpriteService;
import com.tongwan.type.SpriteType;

@Component
public class SpriteServiceImpl implements SpriteService {

	@Autowired
	private MonsterService monsterService;  
	@Override
	public Object[] attributes(Sprite sprite,int[] keys) {
		if(sprite.getType()==SpriteType.MONSTER){
			MonsterDomain monsterDomain = (MonsterDomain) sprite;
			return monsterService.attributes(monsterDomain, keys);
		}
		return null;
	}

}
