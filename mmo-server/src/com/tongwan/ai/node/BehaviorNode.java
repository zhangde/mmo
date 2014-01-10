package com.tongwan.ai.node;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.tongwan.ai.BehaviorActor;
import com.tongwan.ai.type.NodeType;

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
	public abstract NodeType getType();
	/**
	 * 执行AI行为
	 */
	public abstract boolean executeAI(BehaviorActor actor);
	public abstract boolean addChilden(BehaviorNode child);
	public abstract List<BehaviorNode> getChildens();
	public abstract JSONObject toJson();
	public abstract String toString();
	/**
	 * 根据JSON数据生成结点
	 */
	public static BehaviorNode createNode(JSONObject json){
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
				node=new SelectorNode(name,json.getJSONArray(NODE_CHILDENS));
				break;
			case SEQUENCE:
				List<JSONObject> childenList= (List<JSONObject>) json.get(NODE_CHILDENS);
				node=new SequenceNode(name,childenList);
				break;
			case CONDITION:
				node=new ConditionNode(id);
				break;
			case ACTION:
				node=new ActionNode(id);
				break;
			default:
				break;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return node;
	}
}
