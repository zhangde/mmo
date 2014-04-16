package gen.service;
import gen.data.*;
import com.tongwan.common.net.ResultObject;
import com.tongwan.common.net.channel.BaseChannel;
import com.tongwan.common.io.rpc.*;
public abstract class UserInterface extends com.tongwan.net.Handler{
	protected int getModule(){
		return 1;
	}
	public void process(int cmd,BaseChannel channel,RpcInput in) throws Exception{
		int sn=in.readInt();  //指令序号
		switch(cmd){
			case 1 :{
				_login(channel,in,sn);
				return;
			}
			case 2 :{
				_closeUser(channel,in,sn);
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
	public void _closeUser(BaseChannel channel,RpcInput in,int sn) throws Exception{
		String name=in.readString();
		ResultObject<UserVO> result=closeUser(name);
		result.setCmd(2);
		channel.writeResultObject(result);
	}
	public abstract ResultObject<UserVO> login(String name,String password) throws Exception;
	public abstract ResultObject<UserVO> closeUser(String name) throws Exception;
}
