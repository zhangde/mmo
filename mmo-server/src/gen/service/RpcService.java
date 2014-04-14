package gen.service;
import gen.data.*;
import com.tongwan.common.net.ResultObject;
import com.tongwan.common.net.channel.BaseChannel;
import com.tongwan.common.io.rpc.*;
public abstract class RpcService {
	public void process(BaseChannel channel,RpcInput in) throws Exception{
		int cmd=in.readInt(); //指令编号
		int sn=in.readInt();  //指令序号
		switch(cmd){
			case 1 :{
				_login(channel,in,sn);
				return;
			}
			case 5 :{
				_pushSpriteMotion(channel,in,sn);
				return;
			}
			case 4 :{
				_closeUser(channel,in,sn);
				return;
			}
			case 3 :{
				_loadGameMap(channel,in,sn);
				return;
			}
			case 2 :{
				_pushSpriteAdd(channel,in,sn);
				return;
			}
		}
		throw new RuntimeException(" cmd: " + cmd + " not found processor.");
	}
	public void _login(BaseChannel channel,RpcInput in,int sn) throws Exception{
		String name=in.readString();
		String password=in.readString();
		ResultObject<UserVO> result=login(name,password);
		result.setCmd(1);
		channel.writeResultObject(result);
	}
	public void _pushSpriteMotion(BaseChannel channel,RpcInput in,int sn) throws Exception{
		ResultObject<SpriteMotionVO> result=pushSpriteMotion();
		result.setCmd(5);
		channel.writeResultObject(result);
	}
	public void _closeUser(BaseChannel channel,RpcInput in,int sn) throws Exception{
		String name=in.readString();
		ResultObject<UserVO> result=closeUser(name);
		result.setCmd(4);
		channel.writeResultObject(result);
	}
	public void _loadGameMap(BaseChannel channel,RpcInput in,int sn) throws Exception{
		ResultObject<byte[][]> result=loadGameMap();
		result.setCmd(3);
		channel.writeResultObject(result);
	}
	public void _pushSpriteAdd(BaseChannel channel,RpcInput in,int sn) throws Exception{
		ResultObject<SpriteVO> result=pushSpriteAdd();
		result.setCmd(2);
		channel.writeResultObject(result);
	}
	public abstract ResultObject<UserVO> login(String name,String password) throws Exception;
	public abstract ResultObject<SpriteMotionVO> pushSpriteMotion() throws Exception;
	public abstract ResultObject<UserVO> closeUser(String name) throws Exception;
	public abstract ResultObject<byte[][]> loadGameMap() throws Exception;
	public abstract ResultObject<SpriteVO> pushSpriteAdd() throws Exception;
}
