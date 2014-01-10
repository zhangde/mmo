package com.tongwan.common.thread;

import java.util.concurrent.ThreadFactory;

/**
 * 可命名线程工厂
 * @author zhangde
 * @date 2013年12月27日
 */
public class NameThreadFactory implements ThreadFactory{
	/** 线程名前辍 */
	private String namePrefix;
	/** 新建线程编号*/
	private int currentNum=0;
	public NameThreadFactory(String namePrefix){
		this.namePrefix=namePrefix;
	}
	@Override
	public Thread newThread(Runnable r) {
		currentNum++;
		Thread thread=new Thread(r);
		thread.setName(namePrefix+"-"+currentNum);
		return thread;
	}

}
