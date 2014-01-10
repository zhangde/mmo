package com.tongwan.common.ai.behaviortree;

import com.alibaba.fastjson.JSONObject;
import com.tongwan.common.ai.behaviortree.node.BehaviorNode;
import com.tongwan.common.ai.behaviortree.node.SelectorNode;

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
	/**
	 * 行为数据上下文
	 */
	private BehaviorTreeContext context;
	public BehaviorTree(BehaviorTreeContext context,String name){
		this.name=name;
		this.context=context;
		root=new SelectorNode(this,name);
	}
	public BehaviorTree(BehaviorTreeContext context,JSONObject json){
		this.name=json.getString(BehaviorNode.NODE_NAME);
		this.context=context;
		root=new SelectorNode(this,name,json.getJSONArray(BehaviorNode.NODE_CHILDENS));
		
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
	
	public BehaviorTreeContext getContext() {
		return context;
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
