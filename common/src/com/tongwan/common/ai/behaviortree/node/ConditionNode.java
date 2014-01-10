package com.tongwan.common.ai.behaviortree.node;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.tongwan.common.ai.behaviortree.BehaviorActor;
import com.tongwan.common.ai.behaviortree.BehaviorTree;
import com.tongwan.common.ai.behaviortree.type.Condition;
import com.tongwan.common.ai.behaviortree.type.NodeType;

/**
 * 判定条件节点
 * @author zhangde
 * @date 2013年12月24日
 */
public class ConditionNode extends BehaviorNode{
	private Condition condition;
	public ConditionNode(BehaviorTree behaviorTree,int id){
		super(behaviorTree);
		condition=behaviorTree.getContext().getConditionById(id);
	}
	
	@Override
	public NodeType getType() {
		return NodeType.CONDITION;
	}

	@Override
	public boolean executeAI(BehaviorActor actor) {
		return actor.executeDetermine(condition.getKey());
	}
	@Override
	public JSONObject toJson() {
		JSONObject result=new JSONObject();
		result.put(NODE_TYPE, getType());
		result.put(NODE_ID, condition.getId());
		return result;
	}
	@Override
	public String toString() {
		return condition.getName();
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
