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
		}
		throw new RuntimeException(" cmd: " + cmd + " not found processor.");
	}
}
