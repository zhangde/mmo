package com.tongwan.net;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.tongwan.common.io.rpc.RpcInput;
import com.tongwan.common.net.channel.BaseChannel;

/**
 * 基础子令分发器
 * @author zhangde
 *
 */
public abstract class Handler {
	@Autowired
	private ModuleDispatcher dispatcher;
	@PostConstruct
	public final void init(){
		dispatcher.put(getModule(), this);
	}
	protected abstract int getModule();
	
	public abstract void process(int cmd,BaseChannel channel,RpcInput in) throws Exception;
}
