package com.tongwan.ai.node;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tongwan.ai.BehaviorActor;
import com.tongwan.ai.type.NodeType;

/**
 * 顺序执行节点
 * @author zhangde
 * @date 2013年12月25日
 */
public class SequenceNode extends BehaviorNode{
	private String name;
	private List<BehaviorNode> childens=new ArrayList<>();
	public SequenceNode(String name){
		this.name=name;
	}
	public SequenceNode(String name,List<JSONObject> childenList){
		this.name=name;
		if(childenList!=null){
			for(JSONObject map:childenList){
				childens.add(BehaviorNode.createNode(map));
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
