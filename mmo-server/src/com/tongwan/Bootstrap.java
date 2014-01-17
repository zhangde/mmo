package com.tongwan;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tongwan.service.FightService;

/**
 * @author zhangde
 *
 * @date 2014年1月17日
 */
public class Bootstrap {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		FightService fightService=  (FightService) ac.getBean(FightService.class);
		fightService.monster2Monster(null, null);
		
	}

}
