package gen.data ;
import java.util.*;
import com.tongwan.common.io.rpc.*;
public class SpriteVO implements RpcVo{
	public long id;
	public int spriteType;
	public int x;
	public int y;
	public void writeTo(RpcOutput buffer){
		buffer.writeLong(id);
		buffer.writeInt(spriteType);
		buffer.writeInt(x);
		buffer.writeInt(y);
		
	}
	public void read(RpcInput in){
		id=in.readLong();
		spriteType=in.readInt();
		x=in.readInt();
		y=in.readInt();
	}
}
