package com.tongwan.protocol;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.tongwan.common.builder.rpc.RpcMethod;
import com.tongwan.common.builder.rpc.RpcMethodTag;
import com.tongwan.common.builder.rpc.ServiceG;
import com.tongwan.common.net.ResultObject;

/**
 * @author zhangde
 *
 * @date 2014年1月21日
 */
public class CmdService extends Vos{
	
	interface UserInterface{
		@RpcMethodTag(cmd=1,params={"name","password"},remark="登陆")
		public ResultObject<UserVO> login(String name,String password);
		@RpcMethodTag(cmd=4,params={"name"},remark="封号")
		public ResultObject<UserVO> closeUser(String name);
		@RpcMethodTag(cmd=3,params={},remark="加载地图")
		public ResultObject<byte[][]> loadGameMap();
		@RpcMethodTag(cmd=2,params={},remark="推送精灵变更")
		public ResultObject<SpriteVO> pushSpriteAdd();
	}
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		ServiceG.client4CSharpDataStruct("gen.data",Vos.class);
		ServiceG.serverDataStruct("gen.data",Vos.class);
		Class clazz=UserInterface.class;
		Method[] ms =clazz.getDeclaredMethods();
		List<RpcMethod> methods=new ArrayList<>();
		for(Method m:ms){
			methods.add(new RpcMethod(m));
		}
		ServiceG.server("gen.service",methods);
		ServiceG.client4Java("gen.client", methods);
		ServiceG.client4CSharp(clazz, "gen.client", methods);
	}

}
