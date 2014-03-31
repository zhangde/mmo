package gen.data ;
import java.util.*;
import com.tongwan.common.io.rpc.*;
public class PlayerBattleVO implements RpcVo{
	public int id;
	public int level;
	public void writeTo(RpcOutput buffer){
		buffer.writeInt(id);
		buffer.writeInt(level);
		
	}
	public void read(RpcInput in){
		id=in.readInt();
		level=in.readInt();
	}
}
