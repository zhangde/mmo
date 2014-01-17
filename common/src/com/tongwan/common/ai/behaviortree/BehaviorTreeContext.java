/**
 * 
 */
package com.tongwan.common.ai.behaviortree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongwan.common.ai.behaviortree.type.Action;
import com.tongwan.common.ai.behaviortree.type.Condition;
import com.tongwan.common.io.FileX;

/**
 * 行为树数据上下文
 * @author zhangde
 *
 * @date 2014年1月10日
 */
public class BehaviorTreeContext {
	public static final String KEY_TREES="trees";
	public static final String KEY_TREES_NAME="treeName";
	public static final String KEY_ACTIONS="actions";
	public static final String KEY_CONDITIONS="conditions";
	private final List<Condition> conditions=new ArrayList<>();
	private final List<Action> actions=new ArrayList<>();
	private final List<BehaviorTree> trees=new ArrayList<>();
	public void addCondition(Condition condition){
		conditions.add(condition);
	}
	public void addAction(Action action){
		actions.add(action);
	}
	public void AddTree(BehaviorTree tree){
		trees.add(tree);
	}
	/**
	 * 根据ID得到条件
	 * @param id
	 * @return
	 */
	public Condition getConditionById(int id){
		if(conditions!=null && !conditions.isEmpty()){
			for(Condition c:conditions){
				if(c.getId()==id){
					return c;
				}
			}
		}
		return null;
	}
	/**
	 * 根据ID得到动作
	 * @param id
	 * @return
	 */
	public Action getActionById(int id){
		if(actions!=null && !actions.isEmpty()){
			for(Action a:actions){
				if(a.getId()==id){
					return a;
				}
			}
		}
		return null;
	}
	/**
	 * 行为树描述文件
	 * @param path
	 */
	public void load(String content){
	
//			String content=FileX.readAll(path);
			conditions.clear();
			actions.clear();
			trees.clear();
			JSONObject root=(JSONObject) JSON.parse(content);
			List<JSONObject> condJsons=(List<JSONObject>) root.get(KEY_CONDITIONS);
			List<JSONObject> actionJsons=(List<JSONObject>) root.get(KEY_ACTIONS);
			List<JSONObject> treeJsons=(List<JSONObject>) root.get(KEY_TREES);
			int max=0;
			//条件
			for(JSONObject cond:condJsons){
				Condition c=new Condition(cond);
				conditions.add(c);
				if(max<c.getId()){
					max=c.getId();
				}
			}
			Condition.setMaxId(max);
			//动作
			
			if(actionJsons!=null && !actionJsons.isEmpty()){
				max=0;
				for(JSONObject action:actionJsons){
					Action c=new Action(action);
					actions.add(c);
					if(max<c.getId()){
						max=c.getId();
					}
				}
				Action.setMaxId(max);
			}
			//行为树
			
			
			for(JSONObject tree:treeJsons){
				max=0;
				BehaviorTree bTree=new BehaviorTree(this,tree);
				trees.add(bTree);
				if(max<bTree.getId()){
					max=bTree.getId();
				}
				BehaviorTree.setMaxId(max);
			}
			System.out.println(root);
		
	}
	public String toJson(){
		JSONObject root=new JSONObject();
		List<JSONObject> condJsons=new ArrayList<>();
		for(Condition c:conditions){
			condJsons.add(c.toJson());
		}
		List<JSONObject> treeJsons=new ArrayList<>();
		for(BehaviorTree tree:trees){
			treeJsons.add(tree.toJson());
		}
		List<JSONObject> actionJsons=new ArrayList<>();
		for(Action a:actions){
			actionJsons.add(a.toJson());
		}
		root.put(KEY_CONDITIONS, condJsons);
		root.put(KEY_ACTIONS, actionJsons);
		root.put(KEY_TREES, treeJsons);
		return root.toJSONString();
	}
	public List<Condition> getConditions() {
		return conditions;
	}
	public List<Action> getActions() {
		return actions;
	}
	public List<BehaviorTree> getTrees() {
		return trees;
	}
	
}
