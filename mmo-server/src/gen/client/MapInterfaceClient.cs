using UnityEngine;
using System.Collections;
using System;
public abstract class MapInterfaceClient {
	protected BaseChannel channel;
	private int sn=0;
	public void dispath(RpcInput input){
		int cmd=input.readInt();
		switch(cmd){
			case 2 :{
				_pushSpriteAdd(input,sn);
				return;
			}
			case 1 :{
				_loadGameMap(input,sn);
				return;
			}
			case 3 :{
				_pushSpriteMotion(input,sn);
				return;
			}
		}
	}
	public  void pushSpriteAdd(){
		RpcOutput buffer=new RpcOutput();
		buffer.writeInt(2);
		buffer.writeInt(2);
		buffer.writeInt(sn++);
		channel.writeRpcOutput(buffer);
	}
	public  void loadGameMap(){
		RpcOutput buffer=new RpcOutput();
		buffer.writeInt(2);
		buffer.writeInt(1);
		buffer.writeInt(sn++);
		channel.writeRpcOutput(buffer);
	}
	public  void pushSpriteMotion(){
		RpcOutput buffer=new RpcOutput();
		buffer.writeInt(2);
		buffer.writeInt(3);
		buffer.writeInt(sn++);
		channel.writeRpcOutput(buffer);
	}
	private void _pushSpriteAdd(RpcInput input,int sn){
		int state=input.readInt();
		Vos.SpriteVO result=new Vos.SpriteVO();
		result.read(input);
		pushSpriteAddCallback(state,result);
	}
	private void _loadGameMap(RpcInput input,int sn){
		int state=input.readInt();
		byte[][] result=input.readByteArray2();
		loadGameMapCallback(state,result);
	}
	private void _pushSpriteMotion(RpcInput input,int sn){
		int state=input.readInt();
		Vos.SpriteMotionVO result=new Vos.SpriteMotionVO();
		result.read(input);
		pushSpriteMotionCallback(state,result);
	}
	public abstract void pushSpriteAddCallback(int state,Vos.SpriteVO result);

	public abstract void loadGameMapCallback(int state,byte[][] result);

	public abstract void pushSpriteMotionCallback(int state,Vos.SpriteMotionVO result);

}
