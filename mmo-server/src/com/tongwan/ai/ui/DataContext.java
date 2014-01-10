package com.tongwan.ai.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongwan.ai.BehaviorTree;
import com.tongwan.ai.node.ConditionNode;
import com.tongwan.ai.type.Action;
import com.tongwan.ai.type.Condition;
import com.tongwan.common.io.FileX;

/**
 * 数据的上下文环境
 * @author zhangde
 * @date 2013年12月30日
 */
public class DataContext {
	public static final String KEY_TREES="trees";
	public static final String KEY_TREES_NAME="treeName";
	public static final String KEY_ACTIONS="actions";
	public static final String KEY_CONDITIONS="conditions";
	private static String path;
	private static List<Condition> conditions=new ArrayList<>();
	private static List<Action> actions=new ArrayList<>();
	private static List<BehaviorTree> trees=new ArrayList<>();
	/**
	 * 添加新条件
	 * @param name
	 * @param key
	 * @param summary
	 */
	public static boolean addCondition(String name,String key,String summary){
		Condition condition=new Condition(name,key,summary);
		if(!conditions.isEmpty()){
			for(Condition c:conditions){
				if(c.getName().equals(name)){
					JOptionPane.showMessageDialog(null, "该条件名称已存在", "错误",JOptionPane.ERROR_MESSAGE);
					return false;
				}
				if(c.getKey().equals(key)){
					JOptionPane.showMessageDialog(null, "该条件KEY已存在", "错误",JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}
		}
		conditions.add(condition);
		return true;
	}
	/**
	 * 更新条件内容
	 * @param id
	 * @param name
	 * @param key
	 * @param summary
	 */
	public static boolean updateCondition(int id,String name,String key,String summary){
		if(!conditions.isEmpty()){
			Condition current=null;
			for(Condition c:conditions){
				if(c.getId()==id){
					current=c;
				}else{
					if(c.getName().equals(name)){
						JOptionPane.showMessageDialog(null, "该条件名称已存在", "错误",JOptionPane.ERROR_MESSAGE);
						return false;
					}
					if(c.getKey().equals(key)){
						JOptionPane.showMessageDialog(null, "该条件KEY已存在", "错误",JOptionPane.ERROR_MESSAGE);
						return false;
					}
				}
			}
			current.setName(name);
			current.setKey(key);
			current.setSummary(summary);
			return true;
		}
		return false;
	}
	/**
	 * 添加新动作
	 * @param name
	 * @param key
	 * @param summary
	 */
	public static boolean addAction(String name,String key,String summary){
		Action action=new Action(name,key,summary);
		if(!actions.isEmpty()){
			for(Action c:actions){
				if(c.getName().equals(name)){
					JOptionPane.showMessageDialog(null, "动作名称已存在", "错误",JOptionPane.ERROR_MESSAGE);
					return false;
				}
				if(c.getKey().equals(key)){
					JOptionPane.showMessageDialog(null, "动作KEY已存在", "错误",JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}
		}
		actions.add(action);
		return true;
	}
	/**
	 * 更新动作内容
	 * @param id
	 * @param name
	 * @param key
	 * @param summary
	 */
	public static boolean updateAction(int id,String name,String key,String summary){
		if(!actions.isEmpty()){
			Action current=null;
			for(Action c:actions){
				if(c.getId()==id){
					current=c;
				}else{
					if(c.getName().equals(name)){
						JOptionPane.showMessageDialog(null, "该动作名称已存在", "错误",JOptionPane.ERROR_MESSAGE);
						return false;
					}
					if(c.getKey().equals(key)){
						JOptionPane.showMessageDialog(null, "该动作KEY已存在", "错误",JOptionPane.ERROR_MESSAGE);
						return false;
					}
				}
			}
			current.setName(name);
			current.setKey(key);
			current.setSummary(summary);
			return true;
		}
		return false;
	}
	public static boolean addBehaviorTree(BehaviorTree bTree){
		if(!trees.isEmpty()){
			for(BehaviorTree tree:trees){
				if(tree.getName().equals(bTree.getName())){
					JOptionPane.showMessageDialog(null, "该名称已存在", "错误",JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}
		}
		trees.add(bTree);
		return true;
	}
	/**
	 * @return the path
	 */
	public static String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public static void setPath(String _path) {
		path = _path;
	}
	public static String toJson(){
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
	public static void load(String path){
		setPath(path);
		try {
			
			String content=FileX.readAll(path);
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
				BehaviorTree bTree=new BehaviorTree(tree);
				trees.add(bTree);
			}
			System.out.println(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static Condition getConditionById(int id){
		if(conditions!=null && !conditions.isEmpty()){
			for(Condition c:conditions){
				if(c.getId()==id){
					return c;
				}
			}
		}
		return null;
	}
	public static Action getActionById(int id){
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
	 * @return the conditions
	 */
	public static List<Condition> getConditions() {
		return conditions;
	}
	/**
	 * @return the actions
	 */
	public static List<Action> getActions() {
		return actions;
	}
	/**
	 * @return the trees
	 */
	public static List<BehaviorTree> getTrees() {
		return trees;
	}
	
	
}
