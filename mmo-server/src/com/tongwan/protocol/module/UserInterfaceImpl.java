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
import gen.data.UserVO;
import gen.service.UserInterface;

@Component
public class UserInterfaceImpl extends UserInterface {
	
	@Override
	public ResultObject<UserVO> login(String name, String password)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultObject<UserVO> closeUser(String name) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	

}
