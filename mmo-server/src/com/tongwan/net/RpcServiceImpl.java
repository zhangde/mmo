package com.tongwan.net;

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
import gen.data.UserVO;
import gen.service.RpcService;

/**
 * @author zhangde
 *
 * @date 2014年1月24日
 */
@Component
public class RpcServiceImpl extends RpcService{
	@Autowired
	private GameMapService gameMapService;
	@Autowired
	private SpriteService spriteService;
	@Override
	public ResultObject<UserVO> closeUser(String name) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultObject<byte[][]> loadGameMap() throws Exception {
		GameMap map=gameMapService.getGameMap(1);
		ResultObject<byte[][]> result=new ResultObject<>();
		result.setValue(map.getData());
		
		Set<Sprite> monsterSet=map.getAllMonster();
		for(Sprite s:monsterSet){
			SpriteVO v=new SpriteVO();
			v.id=s.getId();
			v.x=s.getX();
			v.y=s.getY();
			v.spriteType=s.getType().ordinal();
			v.keys=AttributeRule.MONSTER_BASE;
			v.values=spriteService.attributes(s, AttributeRule.MONSTER_BASE);
//			ResultObject r=ResultObject.valueOf(2);
//			r.setValue(v);
//			PushHelper.push(r);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see gen.service.RpcService#login(java.lang.String, java.lang.String)
	 */
	@Override
	public ResultObject<UserVO> login(String name, String password)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public ResultObject<SpriteVO> pushSpriteAdd() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultObject<SpriteMotionVO> pushSpriteMotion() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
