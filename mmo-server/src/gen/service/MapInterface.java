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
			case 1 :{
				_loadGameMap(channel,in,sn);
				return;
			}
			case 4 :{
				_loadGameMapComplete(channel,in,sn);
				return;
			}
		}
		throw new RuntimeException(" cmd: " + cmd + " not found processor.");
	}
	public void _loadGameMap(BaseChannel channel,RpcInput in,int sn) throws Exception{
		ResultObject<byte[][]> result=loadGameMap();
		result.setModule(2);
		result.setCmd(1);
		channel.writeResultObject(result);
	}
	public void _loadGameMapComplete(BaseChannel channel,RpcInput in,int sn) throws Exception{
		loadGameMapComplete();
	}
	protected abstract ResultObject<byte[][]> loadGameMap() throws Exception;
	protected abstract void loadGameMapComplete() throws Exception;
	/**
	* 得到推送添加地图精灵(主动推送的结构)
	*/
	public static ResultObject<SpriteVO> GetspriteAddResultObject(){
		ResultObject<SpriteVO> result=ResultObject.valueOf(2,2);
		return result;
	}

	/**
	* 得到推送地图精灵开始移动(主动推送的结构)
	*/
	public static ResultObject<SpriteMotionVO> GetspriteMotionResultObject(){
		ResultObject<SpriteMotionVO> result=ResultObject.valueOf(2,3);
		return result;
	}

}
