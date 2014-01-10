package com.tongwan.ai.node;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tongwan.ai.BehaviorActor;
import com.tongwan.ai.type.NodeType;

/**
 * 选择类型节点
 * @author zhangde
 * @date 2013年12月24日
 */
public class SelectorNode extends BehaviorNode{
	private String name;
	/**
	 * 子节点
	 */
	private List<BehaviorNode> childens=new ArrayList<>();
	public SelectorNode(String name){
		this.name=name;
	}
	public SelectorNode(String name,JSONArray childensList){
		this.name=name;
		if(childensList!=null){
			for(Object map:childensList){
				childens.add(BehaviorNode.createNode((JSONObject)map));
			}
		}
	}
	@Override
	public NodeType getType() {
		return NodeType.SELECTOR;
	}

	@Override
	public boolean executeAI(BehaviorActor actor) {
		for(BehaviorNode node:childens){
			if(node.executeAI(actor)){
				return true;
			}
		}
		return false;
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
