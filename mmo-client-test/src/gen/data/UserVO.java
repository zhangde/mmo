package gen.data ;
import java.util.*;
import com.tongwan.common.builder.rpc.*;
import com.tongwan.common.builder.rpc.io.*;
public class UserVO implements RpcVo{
	public int id;
	public String name;
	public PlayerVO playerVO;
	public List<PlayerBattleVO> playerBattleVOs;
	public void writeTo(RpcOutput buffer){
		buffer.writeInt(id);
		buffer.writeString(name);
		playerVO.writeTo(buffer);
		buffer.writeList(playerBattleVOs);
		
	}
	public void read(RpcInput in){
		id=in.readInt();
		name=in.readString();
		playerVO=in.readObject(PlayerVO.class);
		int size=in.readInt();
		playerBattleVOs=new ArrayList<>();
		for(int i=0;i<size;i++){
			playerBattleVOs.add(in.readObject(PlayerBattleVO.class));
		}
	}
}
