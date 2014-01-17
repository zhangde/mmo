package com.tongwan.ai;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tongwan.common.ai.behaviortree.BehaviorTree;
import com.tongwan.common.ai.behaviortree.BehaviorTreeContext;
import com.tongwan.common.ai.behaviortree.type.Action;
import com.tongwan.common.ai.behaviortree.type.Condition;
import com.tongwan.common.io.FileX;

/**
 * 数据的上下文环境
 * @author zhangde
 * @date 2013年12月30日
 */
public class DataContext {
//	public static final String KEY_TREES="trees";
//	public static final String KEY_TREES_NAME="treeName";
//	public static final String KEY_ACTIONS="actions";
//	public static final String KEY_CONDITIONS="conditions";
	private static String path;
//	private static List<Condition> conditions=new ArrayList<>();
//	private static List<Action> actions=new ArrayList<>();
//	private static List<BehaviorTree> trees=new ArrayList<>();
	public static BehaviorTreeContext context=new BehaviorTreeContext();
	/**
	 * 添加新条件
	 * @param name
	 * @param key
	 * @param summary
	 */
	public static boolean addCondition(String name,String key,String summary){
		Condition condition=new Condition(name,key,summary);
		if(!context.getConditions().isEmpty()){
			for(Condition c:context.getConditions()){
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
		context.addCondition(condition);
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
		if(!context.getConditions().isEmpty()){
			Condition current=null;
			for(Condition c:context.getConditions()){
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
		if(!context.getActions().isEmpty()){
			for(Action c:context.getActions()){
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
		context.addAction(action);
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
		if(!context.getActions().isEmpty()){
			Action current=null;
			for(Action c:context.getActions()){
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
		if(!context.getTrees().isEmpty()){
			for(BehaviorTree tree:context.getTrees()){
				if(tree.getName().equals(bTree.getName())){
					JOptionPane.showMessageDialog(null, "该名称已存在", "错误",JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}
		}
		context.AddTree(bTree);
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
		return context.toJson();
	}
	public static void load(String path){
		setPath(path);
		try {
			context.load(FileX.readAll(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the conditions
	 */
	public static List<Condition> getConditions() {
		return context.getConditions();
	}
	/**
	 * @return the actions
	 */
	public static List<Action> getActions() {
		return context.getActions();
	}
	/**
	 * @return the trees
	 */
	public static List<BehaviorTree> getTrees() {
		return context.getTrees();
	}
	
	
}
