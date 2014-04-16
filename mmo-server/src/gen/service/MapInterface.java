package gen.service;
import gen.data.*;
import com.tongwan.common.net.ResultObject;
import com.tongwan.common.net.channel.BaseChannel;
import com.tongwan.common.io.rpc.*;
public abstract class MapInterface extends com.tongwan.net.Handler{
	protected int getModule(){
		return 2;
	}
	public void process(int cmd,BaseChannel channel,RpcInput in) throws Exception{
		int sn=in.readInt();  //指令序号
		switch(cmd){
			case 2 :{
				_pushSpriteAdd(channel,in,sn);
				return;
			}
			case 1 :{
				_loadGameMap(channel,in,sn);
				return;
			}
			case 3 :{
				_pushSpriteMotion(channel,in,sn);
				return;
			}
		}
		throw new RuntimeException(" cmd: " + cmd + " not found processor.");
	}
	public void _pushSpriteAdd(BaseChannel channel,RpcInput in,int sn) throws Exception{
		ResultObject<SpriteVO> result=pushSpriteAdd();
		result.setCmd(2);
		channel.writeResultObject(result);
	}
	public void _loadGameMap(BaseChannel channel,RpcInput in,int sn) throws Exception{
		ResultObject<byte[][]> result=loadGameMap();
		result.setCmd(1);
		channel.writeResultObject(result);
	}
	public void _pushSpriteMotion(BaseChannel channel,RpcInput in,int sn) throws Exception{
		ResultObject<SpriteMotionVO> result=pushSpriteMotion();
		result.setCmd(3);
		channel.writeResultObject(result);
	}
	public abstract ResultObject<SpriteVO> pushSpriteAdd() throws Exception;
	public abstract ResultObject<byte[][]> loadGameMap() throws Exception;
	public abstract ResultObject<SpriteMotionVO> pushSpriteMotion() throws Exception;
}
