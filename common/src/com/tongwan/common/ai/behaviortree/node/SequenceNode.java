package com.tongwan.common.ai.behaviortree.node;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tongwan.common.ai.behaviortree.BehaviorActor;
import com.tongwan.common.ai.behaviortree.BehaviorTree;
import com.tongwan.common.ai.behaviortree.type.NodeType;

/**
 * 顺序执行节点
 * @author zhangde
 * @date 2013年12月25日
 */
public class SequenceNode extends BehaviorNode{
	private String name;
	private List<BehaviorNode> childens=new ArrayList<>();
	public SequenceNode(BehaviorTree behaviorTree,String name){
		super(behaviorTree);
		this.name=name;
	}
	public SequenceNode(BehaviorTree behaviorTree,String name,List<JSONObject> childenList){
		super(behaviorTree);
		this.name=name;
		if(childenList!=null){
			for(JSONObject map:childenList){
				childens.add(createNode(map));
			}
		}
	}
	@Override
	public NodeType getType() {
		return NodeType.SEQUENCE;
	}

	@Override
	public boolean executeAI(BehaviorActor actor) {
		for(BehaviorNode node:childens){
			if(!node.executeAI(actor)){
				return false;
			}
		}
		return true;
	}
	@Override
	public JSONObject toJson() {
		JSONObject result=new JSONObject();
		result.put(NODE_TYPE, getType());
		result.put(NODE_NAME, name);
		if(!childens.isEmpty()){
			JSONArray jsonArray=new JSONArray();
			for(BehaviorNode node:childens){
				jsonArray.add(node.toJson());
			}
			result.put(NODE_CHILDENS, jsonArray);
		}
		return result;
	}
	@Override
	public String toString() {
		return name;
	}
	@Override
	public boolean addChilden(BehaviorNode child) {
		childens.add(child);
		return true;
	}
	@Override
	public List<BehaviorNode> getChildens() {
		return childens;
	}
}
