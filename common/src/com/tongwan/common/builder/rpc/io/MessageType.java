package com.tongwan.common.builder.rpc.io;


/**
 * PRC消息类型
 * @author zhangde
 *
 * @date 2014年1月22日
 */
public class MessageType {
	public static byte TYPE_NULL=0;
	public static byte TYPE_INT=1;
	public static byte TYPE_STRING=2;
	public static byte TYPE_DOUBLE=3;
	public static byte TYPE_LONG=4;
	public static byte TYPE_BOOLEAN_TRUE=5;
	public static byte TYPE_BOOLEAN_FALSE=6;
	public static byte TYPE_List=7;
	/** 一维字节数组*/
	public static byte TYPE_ARRAY_BYTE=8;
	/** 二维字节数组*/
	public static byte TYPE_ARRAY_BYTE2=9;
}
