using UnityEngine;
using System.Collections;
using System;
public abstract class MapInterfaceClient {
	protected BaseChannel channel;
	private int sn=0;
	public void dispath(RpcInput input){
		int cmd=input.readInt();
		switch(cmd){
			case 1 :{
				_loadGameMap(input,sn);
				return;
			}
			case 2 :{
				_spriteAdd(input,sn);
				return;
			}
			case 3 :{
				_spriteMotion(input,sn);
				return;
			}
		}
	}
	public  void loadGameMap(){
		RpcOutput buffer=new RpcOutput();
		buffer.writeInt(2);//module
		buffer.writeInt(1);//cmd
		buffer.writeInt(sn++);
		channel.writeRpcOutput(buffer);
	}
	public  void loadGameMapComplete(){
		RpcOutput buffer=new RpcOutput();
		buffer.writeInt(2);//module
		buffer.writeInt(4);//cmd
		buffer.writeInt(sn++);
		channel.writeRpcOutput(buffer);
	}
	private void _loadGameMap(RpcInput input,int sn){
		int state=input.readInt();
		byte[][] result=input.readByteArray2();
		loadGameMapCallback(state,result);
	}
	private void _spriteAdd(RpcInput input,int sn){
		int state=input.readInt();
		Vos.SpriteVO result=new Vos.SpriteVO();
		result.read(input);
		spriteAddCallback(state,result);
	}
	private void _spriteMotion(RpcInput input,int sn){
		int state=input.readInt();
		Vos.SpriteMotionVO result=new Vos.SpriteMotionVO();
		result.read(input);
		spriteMotionCallback(state,result);
	}
	protected abstract void loadGameMapCallback(int state,byte[][] result);

	protected abstract void spriteAddCallback(int state,Vos.SpriteVO result);

	protected abstract void spriteMotionCallback(int state,Vos.SpriteMotionVO result);

}
