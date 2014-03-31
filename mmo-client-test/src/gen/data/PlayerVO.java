package gen.data ;
import java.util.*;

import com.tongwan.common.builder.rpc.*;
import com.tongwan.common.builder.rpc.io.*;
import com.tongwan.common.io.rpc.RpcVo;
import com.tongwan.common.io.rpc.impl.RpcInputNettyImpl;
import com.tongwan.common.io.rpc.impl.RpcOutputNettyImpl;
public class PlayerVO implements RpcVo{
	public int golden;
	public int silver;
	public void writeTo(RpcOutputNettyImpl buffer){
		buffer.writeInt(golden);
		buffer.writeInt(silver);
		
	}
	public void read(RpcInputNettyImpl in){
		golden=in.readInt();
		silver=in.readInt();
	}
}
