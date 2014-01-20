package gen.data ;
import java.util.*;
import com.tongwan.common.builder.rpc.*;
import com.tongwan.common.builder.rpc.io.*;
public class PlayerVO implements RpcVo{
	public int golden;
	public int silver;
	public void writeTo(RpcOutput buffer){
		buffer.writeInt(golden);
		buffer.writeInt(silver);
		
	}
	public void read(RpcInput in){
		golden=in.readInt();
		silver=in.readInt();
	}
}
