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
			case 2 :{
				_closeUser(in,sn);
				return;
			}
			case 1 :{
				_login(in,sn);
				return;
			}
		}
	}
	public  void closeUser(String name) throws Exception{
		RpcOutput buffer=new RpcOutputNettyImpl();
		buffer.writeInt(2);
		buffer.writeInt(sn++);
		buffer.writeString(name);
		channel.writeRpcOutput(buffer);
	}
	public  void login(String name,String password) throws Exception{
		RpcOutput buffer=new RpcOutputNettyImpl();
		buffer.writeInt(1);
		buffer.writeInt(sn++);
		buffer.writeString(name);
		buffer.writeString(password);
		channel.writeRpcOutput(buffer);
	}
	private void _closeUser(RpcInput in,int sn) throws Exception{
		int state=in.readInt();
		UserVO result=new UserVO();
		result.read(in);
		closeUserCallback(state,result);
	}
	private void _login(RpcInput in,int sn) throws Exception{
		int state=in.readInt();
		UserVO result=new UserVO();
		result.read(in);
		loginCallback(state,result);
	}
	public abstract void closeUserCallback(int state,UserVO result)throws Exception;

	public abstract void loginCallback(int state,UserVO result)throws Exception;

}
