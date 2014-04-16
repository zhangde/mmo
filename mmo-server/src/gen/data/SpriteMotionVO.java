package gen.data ;
import java.util.*;
import com.tongwan.common.io.rpc.*;
public class SpriteMotionVO implements RpcVo{
	public long id;
	public int spriteType;
	public int[] path;
	public void writeTo(RpcOutput buffer){
		buffer.writeLong(id);
		buffer.writeInt(spriteType);
		buffer.writeIntArray(path);
		
	}
	public void read(RpcInput in){
		id=in.readLong();
		spriteType=in.readInt();
		path=in.readIntArray();
	}
}
