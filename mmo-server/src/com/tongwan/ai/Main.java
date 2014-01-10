package com.tongwan.ai;

import java.io.File;
import java.io.FileInputStream;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tongwan.common.ai.behaviortree.BehaviorTree;
import com.tongwan.common.ai.behaviortree.BehaviorTreeContext;
import com.tongwan.common.io.FileX;
import com.tongwan.module.map.domain.GameMap;
import com.tongwan.module.map.manage.GameMapManage;
import com.tongwan.module.monster.domain.MonsterBattle;
import com.tongwan.module.monster.domain.MonsterDomain;
import com.tongwan.net.TcpBootstrap;

/**
 * @author zhangde
 * @date 2013年12月25日
 */
public class Main {
	public static void main(String[] args) throws Exception{
		AiManage aiManage = new AiManage();
		aiManage.init();
		File file= new File("monster.ai");
		FileInputStream fis=new FileInputStream(file);
		String c=FileX.readAll(file);
		BehaviorTreeContext context=new BehaviorTreeContext();
		context.load("monster.ai");
		JSONObject o=(JSONObject) JSON.parse(c);
		JSONArray oo= (JSONArray) o.get(BehaviorTreeContext.KEY_TREES);
		for(Object treeLevel:oo.toArray()){
			JSONObject _tree=(JSONObject) treeLevel;
//			System.out.println(treeLevel);
			String treeName="";
			BehaviorTree tree=new BehaviorTree(context,_tree);
//			tree.load(_tree.getJSONObject("root"));
			GameMapManage manage=new GameMapManage();
			manage.init();
			GameMap map1=manage.getById(1);
			for(int i=1;i<115;i++){
//				if(i%1000==0){
					MonsterBattle battle=new MonsterBattle();
					battle.setHp(100);
					MonsterDomain monster=new MonsterDomain(i, battle, 0, 0, 5, tree, map1);
					System.out.println("创建monster ["+i+"]");
					aiManage.addActor(monster);
//				}
			}
		}
		
		
		TcpBootstrap bootstrap=new TcpBootstrap();
		bootstrap.start();
	}
}
