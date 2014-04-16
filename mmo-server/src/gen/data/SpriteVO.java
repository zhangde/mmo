package gen.data ;
import java.util.*;
import com.tongwan.common.io.rpc.*;
public class SpriteVO implements RpcVo{
	public long id;
	public int spriteType;
	public int x;
	public int y;
	public int[] keys;
	public Object[] values;
	public void writeTo(RpcOutput buffer){
		buffer.writeLong(id);
		buffer.writeInt(spriteType);
		buffer.writeInt(x);
		buffer.writeInt(y);
		buffer.writeIntArray(keys);
		buffer.writeObjectArray(values);
		
	}
	public void read(RpcInput in){
		id=in.readLong();
		spriteType=in.readInt();
		x=in.readInt();
		y=in.readInt();
		keys=in.readIntArray();
		values=in.readObject(Object[].class);
	}
}
