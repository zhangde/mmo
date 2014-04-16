package com.tongwan.net;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.tongwan.common.io.rpc.RpcInput;
import com.tongwan.common.net.channel.BaseChannel;

/**
 * 模块分发器
 * @author zhangde
 *
 */
@Component
public class ModuleDispatcher {
	private static Log LOG=LogFactory.getLog(ModuleDispatcher.class);
	
	private final Map<Integer,Handler> handlers=new HashMap<>();
	
	public void put(int module,Handler handler){
		handlers.put(module, handler);
	}
	
	public void dispatch(BaseChannel channel,RpcInput in){
		int module=in.readInt();//模块编号
		int cmd=in.readInt(); //指令编号
		Handler handler = handlers.get(module);
		if(handler!=null){
			try {
				handler.process(cmd,channel, in);
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error("错误",e);
			}
		}else{
			LOG.error("找不到指定模块的分发器 ["+module+"]");
		}
	}
}
