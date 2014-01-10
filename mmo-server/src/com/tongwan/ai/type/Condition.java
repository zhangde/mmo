package com.tongwan.ai.type;

import com.alibaba.fastjson.JSONObject;
import com.tongwan.ai.node.BehaviorNode;

/**
 * 判定条件数据
 * 
 * @author zhangde
 * @date 2014年1月1日
 */
public class Condition {
	private static int maxId = 1;
	private int id;
	private String name;
	private String key;
	private String summary;

	public Condition(String name, String key, String summary) {
		this.name = name;
		this.key = key;
		this.summary = summary;
		this.id = ++maxId;
	}

	public Condition(JSONObject json) {
		this.name = json.getString(BehaviorNode.NODE_NAME);
		this.key = json.getString(BehaviorNode.NODE_KEY);
		this.summary = json.getString(BehaviorNode.NODE_SUMMARY);
		this.id = json.getIntValue(BehaviorNode.NODE_ID);
	}

	public JSONObject toJson() {
		JSONObject result = new JSONObject();
		result.put(BehaviorNode.NODE_ID, id);
		result.put(BehaviorNode.NODE_NAME, name);
		result.put(BehaviorNode.NODE_KEY, key);
		result.put(BehaviorNode.NODE_SUMMARY, summary);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary
	 *            the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @param maxId
	 *            the maxId to set
	 */
	public static void setMaxId(int maxId) {
		Condition.maxId = maxId;
	}

}
