package com.tongwan.net;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tongwan.common.builder.rpc.RpcMethod;
import com.tongwan.common.builder.rpc.RpcMethodTag;
import com.tongwan.common.builder.rpc.ServiceG;
import com.tongwan.common.net.ResultObject;

/**
 * @author zhangde
 *
 * @date 2014年1月21日
 */
public class CmdService {
	class UserVO{
		public int id;
		public String name;
		public PlayerVO playerVO;
		List<PlayerBattleVO> playerBattleVOs;
	}
	class PlayerVO{
		public int golden;
		public int silver;
	}
	class PlayerBattleVO{
		public int id;
		public int level;
	}
	class SpriteVO{
		public int id;
		public int x;
		public int y;
	}
	interface RpcInterface{
		@RpcMethodTag(cmd=1,params={"name","password"},remark="登陆")
		public ResultObject<UserVO> login(String name,String password);
		@RpcMethodTag(cmd=4,params={"name"},remark="封号")
		public ResultObject<UserVO> closeUser(String name);
		@RpcMethodTag(cmd=3,params={},remark="加载地图")
		public ResultObject<byte[][]> loadGameMap();
		@RpcMethodTag(cmd=2,params={},remark="推送精灵变更")
		public ResultObject<SpriteVO> pushSpriteChange();
	}
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		ServiceG.serverDataStruct("gen.data",CmdService.class);
		Class clazz=RpcInterface.class;
		Method[] ms =clazz.getDeclaredMethods();
		List<RpcMethod> methods=new ArrayList<>();
		for(Method m:ms){
			methods.add(new RpcMethod(m));
		}
		ServiceG.server("gen.service",methods);
		ServiceG.client4Java("gen.client", methods);
	}

}
