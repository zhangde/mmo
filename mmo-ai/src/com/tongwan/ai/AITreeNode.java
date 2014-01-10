package com.tongwan.ai;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import com.alibaba.fastjson.JSONObject;
import com.tongwan.common.ai.behaviortree.node.BehaviorNode;
import com.tongwan.common.ai.behaviortree.type.NodeType;

/**
 * AI行为容器树节点
 * @author zhangde
 * @date 2013年12月27日
 */
public class AITreeNode extends DefaultMutableTreeNode{
	private BehaviorNode behaviorNode;
	public AITreeNode(BehaviorNode behaviorNode){
		super(behaviorNode);
		this.behaviorNode=behaviorNode;
		List<BehaviorNode> childens=behaviorNode.getChildens();
		if(childens!=null && !childens.isEmpty()){
			for(BehaviorNode c:childens){
				this.add(new AITreeNode(c));
			}
		}
	}

	/**
	 * @return the type
	 */
	public NodeType getType() {
		return behaviorNode.getType();
	}
	/**
	 * @return the behaviorNode
	 */
	public BehaviorNode getBehaviorNode() {
		return behaviorNode;
	}
	public JSONObject toJson(){
		return behaviorNode.toJson();
	}
}
