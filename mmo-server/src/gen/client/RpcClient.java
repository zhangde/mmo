package gen.client;
import gen.data.*;
import com.tongwan.common.net.ResultObject;
import com.tongwan.common.net.channel.BaseChannel;
import com.tongwan.common.io.rpc.*;
import com.tongwan.common.io.rpc.impl.*;
public abstract class RpcClient {
	protected BaseChannel channel;
	private int sn=0;
	public void dispath(RpcInput in)throws Exception{
		int cmd=in.readInt();
		switch(cmd){
			case 1 :{
				_loadGameMap(in,sn);
				return;
			}
			case 2 :{
				_spriteAdd(in,sn);
				return;
			}
			case 3 :{
				_spriteMotion(in,sn);
				return;
			}
		}
	}
	public  void loadGameMap() throws Exception{
		RpcOutput buffer=new RpcOutputNettyImpl();
		buffer.writeInt(1);
		buffer.writeInt(sn++);
		channel.writeRpcOutput(buffer);
	}
	public  void spriteAdd() throws Exception{
		RpcOutput buffer=new RpcOutputNettyImpl();
		buffer.writeInt(2);
		buffer.writeInt(sn++);
		channel.writeRpcOutput(buffer);
	}
	public  void spriteMotion() throws Exception{
		RpcOutput buffer=new RpcOutputNettyImpl();
		buffer.writeInt(3);
		buffer.writeInt(sn++);
		channel.writeRpcOutput(buffer);
	}
	public  void loadGameMapComplete() throws Exception{
		RpcOutput buffer=new RpcOutputNettyImpl();
		buffer.writeInt(4);
		buffer.writeInt(sn++);
		channel.writeRpcOutput(buffer);
	}
	private void _loadGameMap(RpcInput in,int sn) throws Exception{
		int state=in.readInt();
		byte[][] result=in.readByteArray2();
		loadGameMapCallback(state,result);
	}
	private void _spriteAdd(RpcInput in,int sn) throws Exception{
		int state=in.readInt();
		SpriteVO result=new SpriteVO();
		result.read(in);
		spriteAddCallback(state,result);
	}
	private void _spriteMotion(RpcInput in,int sn) throws Exception{
		int state=in.readInt();
		SpriteMotionVO result=new SpriteMotionVO();
		result.read(in);
		spriteMotionCallback(state,result);
	}

	public abstract void loadGameMapCallback(int state,byte[][] result)throws Exception;

	public abstract void spriteAddCallback(int state,SpriteVO result)throws Exception;

	public abstract void spriteMotionCallback(int state,SpriteMotionVO result)throws Exception;


}
