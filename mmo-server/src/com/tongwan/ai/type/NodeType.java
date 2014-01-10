package com.tongwan.ai.type;

/**
 * 结点类型
 * @author zhangde
 * @date 2013年12月24日
 */
public enum NodeType {
	/**
	 *  选择节点
	 *  顺序遍历子节点，直到一个子节点返回为TRUE 停止遍历，
	 *  如果所有子节点都返回FALSE 则当前节点返回FALSE,其它返回TRUE
	 * */
	SELECTOR,
	/**
	 * 顺序节点
	 * 顺序遍历子节点，直到一个子节点返回为FALSE 停止遍历，
	 * 如果所有子节点返回TRUE 则当前节点返回TRUE，其它返回FALSE
	 */
	SEQUENCE,
	/**
	 * 条件节点
	 * 条件成立返回TRUE，不成立返回FALSE
	 */
	CONDITION,
	/**
	 * 动作节点
	 * 动作执行成功返回为TRUE，失败返回FALSE
	 */
	ACTION
}
