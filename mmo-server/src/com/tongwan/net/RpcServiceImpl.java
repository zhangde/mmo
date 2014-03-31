package com.tongwan.net;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tongwan.common.net.ResultObject;
import com.tongwan.domain.map.GameMap;
import com.tongwan.service.GameMapService;

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
	/* (non-Javadoc)
	 * @see gen.service.RpcService#closeUser(java.lang.String)
	 */
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
	public ResultObject<SpriteVO> pushSpriteChange() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
