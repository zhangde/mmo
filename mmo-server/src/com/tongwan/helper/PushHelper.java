package com.tongwan.helper;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tongwan.common.net.ResultObject;
import com.tongwan.common.thread.NameThreadFactory;
import com.tongwan.manage.SessionManage;

/**
 * 异步推送助手
 * @author zhangde
 * @date 2014年4月2日
 */
@Component
public class PushHelper {
	@Autowired
	private SessionManage sessionManage;
	
	private static PushHelper instatnce;
	private static final ExecutorService EXECUTOR=Executors.newFixedThreadPool(1, new NameThreadFactory("异步推送线程"));
	@PostConstruct
	public void init(){
		instatnce=this;
	}
	public static void push(final long playerId,final ResultObject result){
		EXECUTOR.submit(new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				instatnce.sessionManage.writeTo(playerId, result);
			}
		});
	}
	public static void push(final ResultObject result){
		EXECUTOR.submit(new Runnable() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				instatnce.sessionManage.writeToAllOnline(result);
			}
		});
	}
}
