package gen.client;
import gen.data.*;

import com.tongwan.common.io.rpc.impl.RpcInputNettyImpl;
import com.tongwan.common.io.rpc.impl.RpcOutputNettyImpl;
import com.tongwan.common.net.ResultObject;
import com.tongwan.common.net.channel.BaseChannel;
import com.tongwan.common.builder.rpc.io.*;
public abstract class RpcClient {
	protected BaseChannel channel;
	private int sn=0;
	public void dispath(RpcInputNettyImpl in)throws Exception{
		int cmd=in.readInt();
		switch(cmd){
			case 4 :{
				_closeUser(in,sn);
				return;
			}
			case 3 :{
				_loadGameMap(in,sn);
				return;
			}
			case 2 :{
				_pushSpriteChange(in,sn);
				return;
			}
			case 1 :{
				_login(in,sn);
				return;
			}
		}
	}
	public  void closeUser(String name) throws Exception{
		RpcOutputNettyImpl buffer=new RpcOutputNettyImpl();
		buffer.writeInt(4);
		buffer.writeInt(sn++);
		buffer.writeString(name);
		channel.writeRpcOutput(buffer);
	}
	public  void loadGameMap() throws Exception{
		RpcOutputNettyImpl buffer=new RpcOutputNettyImpl();
		buffer.writeInt(3);
		buffer.writeInt(sn++);
		channel.writeRpcOutput(buffer);
	}
	public  void pushSpriteChange() throws Exception{
		RpcOutputNettyImpl buffer=new RpcOutputNettyImpl();
		buffer.writeInt(2);
		buffer.writeInt(sn++);
		channel.writeRpcOutput(buffer);
	}
	public  void login(String name,String password) throws Exception{
		RpcOutputNettyImpl buffer=new RpcOutputNettyImpl();
		buffer.writeInt(1);
		buffer.writeInt(sn++);
		buffer.writeString(name);
		buffer.writeString(password);
		channel.writeRpcOutput(buffer);
	}
	private void _closeUser(RpcInputNettyImpl in,int sn) throws Exception{
		int state=in.readInt();
		UserVO result=new UserVO();
		result.read(in);
		closeUserCallback(state,result);
	}
	private void _loadGameMap(RpcInputNettyImpl in,int sn) throws Exception{
		int state=in.readInt();
		byte[][] result=in.readByteArray2();
		loadGameMapCallback(state,result);
	}
	private void _pushSpriteChange(RpcInputNettyImpl in,int sn) throws Exception{
		int state=in.readInt();
		SpriteVO result=new SpriteVO();
		result.read(in);
		pushSpriteChangeCallback(state,result);
	}
	private void _login(RpcInputNettyImpl in,int sn) throws Exception{
		int state=in.readInt();
		UserVO result=new UserVO();
		result.read(in);
		loginCallback(state,result);
	}
	public abstract void closeUserCallback(int state,UserVO result)throws Exception;

	public abstract void loadGameMapCallback(int state,byte[][] result)throws Exception;

	public abstract void pushSpriteChangeCallback(int state,SpriteVO result)throws Exception;

	public abstract void loginCallback(int state,UserVO result)throws Exception;

}
