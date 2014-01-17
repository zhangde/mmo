package com.tongwan.common.ai.behaviortree.node;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.tongwan.common.ai.behaviortree.BehaviorActor;
import com.tongwan.common.ai.behaviortree.BehaviorTree;
import com.tongwan.common.ai.behaviortree.type.NodeType;

/**
 * 基础节点
 * @author zhangde
 *
 */

public abstract class BehaviorNode {
	public static String NODE_ID="id";
	public static String NODE_NAME="name";
	public static String NODE_TYPE="type";
	public static String NODE_CHILDENS="childens";
	public static String NODE_KEY="key";
	public static String NODE_ACTION="action";
	public static String NODE_SUMMARY="summary";
	private BehaviorTree behaviorTree;
	public BehaviorNode(BehaviorTree behaviorTree){
		this.behaviorTree=behaviorTree;
	}
	public abstract NodeType getType();
	/**
	 * 执行AI行为
	 */
	public abstract boolean executeAI(BehaviorActor actor);
	public abstract boolean addChilden(BehaviorNode child);
	public abstract List<BehaviorNode> getChildens();
	public abstract JSONObject toJson();
	public abstract String toString();
	public BehaviorTree getBehaviorTree(){
		return this.behaviorTree;
	}
	/**
	 * 根据JSON数据生成结点
	 */
	public BehaviorNode createNode(JSONObject json){
		BehaviorNode node=null;
		String nodeType=(String) json.get(NODE_TYPE);
		try{
			NodeType type = NodeType.valueOf(nodeType);
			int id=json.getIntValue(NODE_ID);
			String key = json.getString(NODE_KEY);
			String name= json.getString(NODE_NAME);
			String summary = json.getString(NODE_SUMMARY);
			switch (type) {
			case SELECTOR:
				node=new SelectorNode(behaviorTree,name,json.getJSONArray(NODE_CHILDENS));
				break;
			case SEQUENCE:
				List<JSONObject> childenList= (List<JSONObject>) json.get(NODE_CHILDENS);
				node=new SequenceNode(behaviorTree,name,childenList);
				break;
			case CONDITION:
				node=new ConditionNode(behaviorTree,id);
				break;
			case ACTION:
				node=new ActionNode(behaviorTree,id);
				break;
			default:
				break;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return node;
	}
	/**
	 * 删除数据节点
	 * @param child
	 */
	public boolean removeNode(BehaviorNode child){
		List<BehaviorNode> childens= getChildens();
		if(childens!=null && !childens.isEmpty()){
			for(BehaviorNode node:childens){
				if(node.equals(child)){
					childens.remove(node);
					return true;
				}else{
					List<BehaviorNode> childens2= node.getChildens();
					if(childens2!=null && !childens2.isEmpty()){
						if(node.removeNode(child)){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
