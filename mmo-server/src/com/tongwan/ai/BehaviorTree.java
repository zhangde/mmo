package com.tongwan.ai;

import com.alibaba.fastjson.JSONObject;
import com.tongwan.ai.node.BehaviorNode;
import com.tongwan.ai.node.SelectorNode;

/**
 * AI 行为树
 * @author zhangde
 *
 */
public class BehaviorTree {
	/**
	 * 名字
	 */
	private String name;
	/**
	 * 根节点
	 */
	private BehaviorNode root;
	public BehaviorTree(String name){
		this.name=name;
		root=new SelectorNode(name);
	}
	public BehaviorTree(JSONObject json){
		this.name=json.getString(BehaviorNode.NODE_NAME);
		root=new SelectorNode(name,json.getJSONArray(BehaviorNode.NODE_CHILDENS));
		
	}
	/**
	 * 运行AI行为
	 * 默认一级子目录为SELECTOR类型节点
	 */
	public boolean run(BehaviorActor actor){
		return root.executeAI(actor);
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return the root
	 */
	public BehaviorNode getRoot() {
		return root;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return  name ;
	}
	public JSONObject toJson(){
		return root.toJson();
	}
	
}
