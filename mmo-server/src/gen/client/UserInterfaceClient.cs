using UnityEngine;
using System.Collections;
using System;
public abstract class UserInterfaceClient {
	protected BaseChannel channel;
	private int sn=0;
	public void dispath(RpcInput input){
		int cmd=input.readInt();
		switch(cmd){
			case 1 :{
				_login(input,sn);
				return;
			}
			case 5 :{
				_pushSpriteMotion(input,sn);
				return;
			}
			case 4 :{
				_closeUser(input,sn);
				return;
			}
			case 3 :{
				_loadGameMap(input,sn);
				return;
			}
			case 2 :{
				_pushSpriteAdd(input,sn);
				return;
			}
		}
	}
	public  void login(String name,String password){
		RpcOutput buffer=new RpcOutput();
		buffer.writeInt(1);
		buffer.writeInt(sn++);
		buffer.writeString(name);
		buffer.writeString(password);
		channel.writeRpcOutput(buffer);
	}
	public  void pushSpriteMotion(){
		RpcOutput buffer=new RpcOutput();
		buffer.writeInt(5);
		buffer.writeInt(sn++);
		channel.writeRpcOutput(buffer);
	}
	public  void closeUser(String name){
		RpcOutput buffer=new RpcOutput();
		buffer.writeInt(4);
		buffer.writeInt(sn++);
		buffer.writeString(name);
		channel.writeRpcOutput(buffer);
	}
	public  void loadGameMap(){
		RpcOutput buffer=new RpcOutput();
		buffer.writeInt(3);
		buffer.writeInt(sn++);
		channel.writeRpcOutput(buffer);
	}
	public  void pushSpriteAdd(){
		RpcOutput buffer=new RpcOutput();
		buffer.writeInt(2);
		buffer.writeInt(sn++);
		channel.writeRpcOutput(buffer);
	}
	private void _login(RpcInput input,int sn){
		int state=input.readInt();
		Vos.UserVO result=new Vos.UserVO();
		result.read(input);
		loginCallback(state,result);
	}
	private void _pushSpriteMotion(RpcInput input,int sn){
		int state=input.readInt();
		Vos.SpriteMotionVO result=new Vos.SpriteMotionVO();
		result.read(input);
		pushSpriteMotionCallback(state,result);
	}
	private void _closeUser(RpcInput input,int sn){
		int state=input.readInt();
		Vos.UserVO result=new Vos.UserVO();
		result.read(input);
		closeUserCallback(state,result);
	}
	private void _loadGameMap(RpcInput input,int sn){
		int state=input.readInt();
		byte[][] result=input.readByteArray2();
		loadGameMapCallback(state,result);
	}
	private void _pushSpriteAdd(RpcInput input,int sn){
		int state=input.readInt();
		Vos.SpriteVO result=new Vos.SpriteVO();
		result.read(input);
		pushSpriteAddCallback(state,result);
	}
	public abstract void loginCallback(int state,Vos.UserVO result);

	public abstract void pushSpriteMotionCallback(int state,Vos.SpriteMotionVO result);

	public abstract void closeUserCallback(int state,Vos.UserVO result);

	public abstract void loadGameMapCallback(int state,byte[][] result);

	public abstract void pushSpriteAddCallback(int state,Vos.SpriteVO result);

}
