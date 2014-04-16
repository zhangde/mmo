using System.Collections;
using System.Collections.Generic;
public class Vos{
	public class PlayerBattleVO : RpcVo{
		public int id;
		public int level;
		public void writeTo(RpcOutput buffer){
			buffer.writeInt(id);
			buffer.writeInt(level);
		
		}
		public void read(RpcInput input){
			id=input.readInt();
			level=input.readInt();
		}
	}

	public class PlayerVO : RpcVo{
		public int golden;
		public int silver;
		public void writeTo(RpcOutput buffer){
			buffer.writeInt(golden);
			buffer.writeInt(silver);
		
		}
		public void read(RpcInput input){
			golden=input.readInt();
			silver=input.readInt();
		}
	}

	public class UserVO : RpcVo{
		public int id;
		public string name;
		public PlayerVO playerVO;
		public List<PlayerBattleVO> playerBattleVOs;
		public void writeTo(RpcOutput buffer){
			buffer.writeInt(id);
			buffer.writeString(name);
			playerVO.writeTo(buffer);
			buffer.writeList(playerBattleVOs);
		
		}
		public void read(RpcInput input){
			id=input.readInt();
			name=input.readString();
			playerVO = new PlayerVO();
			playerVO.read(input);
			int size=input.readInt();
			playerBattleVOs=new List<PlayerBattleVO>();
			for(int i=0;i<size;i++){
				PlayerBattleVO _PlayerBattleVO = new PlayerBattleVO();
				_PlayerBattleVO.read(input);
				playerBattleVOs.Add(_PlayerBattleVO);
			}
		}
	}

}
