package com.tongwan.protocol;

import java.util.List;

public class Vos {
	public class UserVO{
		public int id;
		public String name;
		public PlayerVO playerVO;
		List<PlayerBattleVO> playerBattleVOs;
	}
	public class PlayerVO{
		public int golden;
		public int silver;
	}
	public class PlayerBattleVO{
		public int id;
		public int level;
	}
	public class SpriteVO{
		public long id;
		public int x;
		public int y;
	}
}
