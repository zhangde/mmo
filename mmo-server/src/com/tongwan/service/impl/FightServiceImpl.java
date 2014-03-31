package com.tongwan.service.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.tongwan.domain.monster.MonsterDomain;
import com.tongwan.service.FightService;

/**
 * @author zhangde
 *
 * @date 2014年1月11日
 */
@Service
public class FightServiceImpl implements FightService {


	@Override
	public void monster2Monster(MonsterDomain attacker, MonsterDomain target) {
		//System.out.println("attacker"+"-target");
	}

}
