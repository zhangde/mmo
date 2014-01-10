package com.tongwan.common.ai.behaviortree.node;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.tongwan.common.ai.behaviortree.BehaviorActor;
import com.tongwan.common.ai.behaviortree.BehaviorTree;
import com.tongwan.common.ai.behaviortree.type.Action;
import com.tongwan.common.ai.behaviortree.type.NodeType;

/**
 * 动作结点
 * @author zhangde
 * @date 2013年12月24日
 */
public class ActionNode extends BehaviorNode {
	private Action action;
	public ActionNode(BehaviorTree behaviorTree,int id){
		super(behaviorTree);
		action=behaviorTree.getContext().getActionById(id);
	}
	@Override
	public NodeType getType() {
		return NodeType.ACTION;
	}

	@Override
	public boolean executeAI(BehaviorActor actor) {
		return actor.executeAction(action.getKey());
	}
	@Override
	public JSONObject toJson() {
		JSONObject result=new JSONObject();
		result.put(NODE_TYPE, getType());
		result.put(NODE_ID, action.getId());
		return result;
	}
	@Override
	public String toString() {
		return action.getName();
	}
	@Override
	public boolean addChilden(BehaviorNode child) {
		return false;
	}
	@Override
	public List<BehaviorNode> getChildens() {
		return null;
	}

}
