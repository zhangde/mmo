package gen.data ;
import java.util.*;

import com.tongwan.common.builder.rpc.*;
import com.tongwan.common.builder.rpc.io.*;
import com.tongwan.common.io.rpc.RpcVo;
import com.tongwan.common.io.rpc.impl.RpcInputNettyImpl;
import com.tongwan.common.io.rpc.impl.RpcOutputNettyImpl;
public class PlayerBattleVO implements RpcVo{
	public int id;
	public int level;
	public void writeTo(RpcOutputNettyImpl buffer){
		buffer.writeInt(id);
		buffer.writeInt(level);
		
	}
	public void read(RpcInputNettyImpl in){
		id=in.readInt();
		level=in.readInt();
	}
}
