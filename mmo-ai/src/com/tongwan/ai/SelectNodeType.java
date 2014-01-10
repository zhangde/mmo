package com.tongwan.ai;

import com.tongwan.common.ai.behaviortree.type.NodeType;

/**
 * @author zhangde
 * @date 2013-12-29
 */
public class SelectNodeType {
	private NodeType type;
	private String name;
	public SelectNodeType(NodeType type,String name){
		this.type=type;
		this.name=name;
	}
	/**
	 * @return the type
	 */
	public NodeType getType() {
		return type;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return name;
	}
	
}
