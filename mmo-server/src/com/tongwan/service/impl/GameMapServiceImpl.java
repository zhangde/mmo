package com.tongwan.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.tongwan.context.event.GameMapLoadedEvent;
import com.tongwan.context.event.ResourceLoadedEvent;
import com.tongwan.domain.map.GameMap;
import com.tongwan.service.GameMapService;

/**
 * @author zhangde
 *
 * @date 2014年1月17日
 */
@Service
public class GameMapServiceImpl implements GameMapService ,ApplicationContextAware,ApplicationListener<ResourceLoadedEvent>{
	private ApplicationContext context;
	private  Map<Integer, GameMap> gameMaps=new HashMap<Integer, GameMap>();
	public  void init(){
		byte[][] data1={
		    {0,0,0,0,1,1,0,0,0,0,0,0,0,0},
		    {0,0,0,0,1,1,0,0,0,0,0,0,0,0},
		    {0,0,0,0,1,1,0,0,0,0,0,0,0,0},
		    {0,0,0,0,1,1,0,0,0,0,0,0,0,0},
		    {0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		    {0,0,0,0,1,1,0,0,0,0,0,0,0,0},
		    {0,0,0,0,1,1,0,0,0,0,0,0,0,0},
		    {0,0,0,0,1,1,0,0,0,0,0,0,0,0},
		    {0,0,0,0,1,1,0,0,0,0,0,0,0,0},
		    {0,0,0,0,1,1,0,0,0,0,0,0,0,0},
		    {0,0,0,0,1,1,0,0,0,0,0,0,0,0},
		    {0,0,0,0,1,1,0,0,0,0,0,0,0,0},
		    {0,0,0,0,1,1,0,0,0,0,0,0,0,0},
		    {0,0,0,0,1,1,0,0,0,0,0,0,0,0}
		}; 
		GameMap gameMap1=new GameMap(data1);
		gameMaps.put(1, gameMap1);
	}
	@Override
	public GameMap getGameMap(int id) {
		return gameMaps.get(id);
	}

	@Override
	public void onApplicationEvent(ResourceLoadedEvent event) {
		init();
		context.publishEvent(new GameMapLoadedEvent(context));
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context=applicationContext;
		
	}

}
