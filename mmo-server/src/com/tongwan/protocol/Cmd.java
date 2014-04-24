package com.tongwan.protocol;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.tongwan.common.builder.rpc.RpcMethod;
import com.tongwan.common.builder.rpc.RpcMethodTag;
import com.tongwan.common.builder.rpc.RPCTMode;
import com.tongwan.common.builder.rpc.ServiceG;
import com.tongwan.common.net.ResultObject;

/**
 * @author zhangde
 *
 * @date 2014年1月21日
 */
public class Cmd extends Vos{
	
	interface UserInterface{
		final static int module=Module.USER;
//		@RpcMethodTag(cmd=1,push=false,params={"name","password"},remark="登陆")
//		public ResultObject<UserVO> login(String name,String password);
//		@RpcMethodTag(cmd=2,push=false,params={"name"},remark="封号")
//		public ResultObject<UserVO> closeUser(String name);
		
	}
	interface MapInterface{
		final static int module=Module.MAP;
		@RpcMethodTag(cmd=1,mode=RPCTMode.SEND_RETURN,params={},remark="加载地图")
		public ResultObject<byte[][]> loadGameMap();
		@RpcMethodTag(cmd=4,mode=RPCTMode.ONLY_SEND,params={},remark="加载地图成功")
		public void loadGameMapComplete();
		@RpcMethodTag(cmd=2,mode=RPCTMode.ONLY_PUSH,params={},remark="推送添加地图精灵")
		public ResultObject<SpriteVO> spriteAdd();
		@RpcMethodTag(cmd=3,mode=RPCTMode.ONLY_PUSH,params={},remark="推送地图精灵开始移动")
		public ResultObject<SpriteMotionVO> spriteMotion();
	}
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		ServiceG.client4CSharpDataStruct("gen.data",Vos.class);
		ServiceG.serverDataStruct("gen.data",Vos.class);
		gRpc(UserInterface.class);
		gRpc(MapInterface.class);
	}
	
	public static void gRpc(Class clazz) throws Exception{
		Method[] ms =clazz.getDeclaredMethods();
		Field field= clazz.getDeclaredField("module");
		int module=(int) field.get(null);
		List<RpcMethod> methods=new ArrayList<>();
		for(Method m:ms){
			methods.add(new RpcMethod(m));
		}
		ServiceG.server(module,"gen.service",clazz.getSimpleName(),methods);
		ServiceG.client4Java("gen.client", methods);
		ServiceG.client4CSharp(module,clazz, "gen.client", methods);
	}
}
