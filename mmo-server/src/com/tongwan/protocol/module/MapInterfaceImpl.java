package com.tongwan.protocol.module;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tongwan.common.net.ResultObject;
import com.tongwan.domain.attribute.AttributeRule;
import com.tongwan.domain.map.GameMap;
import com.tongwan.domain.sprite.Sprite;
import com.tongwan.helper.PushHelper;
import com.tongwan.service.GameMapService;
import com.tongwan.service.SpriteService;

import gen.data.SpriteMotionVO;
import gen.data.SpriteVO;
import gen.service.MapInterface;

@Component
public class MapInterfaceImpl extends MapInterface{
	@Autowired
	private GameMapService gameMapService;
	@Autowired
	private SpriteService spriteService;
	@Override
	protected ResultObject<byte[][]> loadGameMap() throws Exception {
		GameMap map=gameMapService.getGameMap(1);
		ResultObject<byte[][]> result=new ResultObject<>();
		result.setValue(map.getData());
		
		
		return result;
	}
	
	@Override
	protected void loadGameMapComplete() throws Exception {
		GameMap map=gameMapService.getGameMap(1);
		Set<Sprite> monsterSet=map.getAllMonster();
		for(Sprite s:monsterSet){
			SpriteVO v=new SpriteVO();
			v.id=s.getId();
			v.x=s.getX();
			v.y=s.getY();
			v.spriteType=s.getType().ordinal();
			v.keys=AttributeRule.MONSTER_BASE;
			v.values=spriteService.attributes(s, AttributeRule.MONSTER_BASE);
			ResultObject r=MapInterface.GetspriteAddResultObject();
			r.setValue(v);
			PushHelper.push(r);
		}
	}

	
}
