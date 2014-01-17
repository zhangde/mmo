package com.tongwan.helper;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tongwan.domain.monster.MonsterDomain;
import com.tongwan.service.FightService;

/**
 * @author zhangde
 *
 * @date 2014年1月11日
 */
@Component
public class FightHelper {
	@Autowired
	private FightService fightService;
	private static FightHelper instatnce; 
	@PostConstruct
	public void init(){
		instatnce=this;
	}
	public static void fight(MonsterDomain attacker,MonsterDomain target){
		instatnce.fightService.monster2Monster(attacker, target);
	}
}
