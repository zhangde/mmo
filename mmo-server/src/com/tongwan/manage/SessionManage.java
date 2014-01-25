package com.tongwan.manage;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tongwan.common.net.channel.BaseChannel;

/**
 * session 管理
 * @author zhangde
 *
 * @date 2014年1月25日
 */
public class SessionManage {
	static Log LOG=LogFactory.getLog(SessionManage.class);
	/** 匿名连接 未登陆用户*/
	private static final ConcurrentHashMap<Integer, BaseChannel> ANONYMOUS_CHANNELS=new ConcurrentHashMap<>();
	/** 在线用户 */
	private static final ConcurrentHashMap<Long, BaseChannel> ONLINE_CHANNELS=new ConcurrentHashMap<>();
	/**
	 * 加入匿名连接列表
	 * @param channel
	 */
	public void putToAnonymousList(BaseChannel channel){
		if(channel!=null){
			ANONYMOUS_CHANNELS.put(channel.getId(), channel);
		}
	}
	/**
	 * 加入在线角色列表
	 * @param playerId 角色id
	 * @param channel
	 */
	public void putToOnlineList(long playerId,BaseChannel channel){
		if(channel==null){
			return;
		}
		int channelId=channel.getId();
		if(ANONYMOUS_CHANNELS.contains(channelId)){
			ANONYMOUS_CHANNELS.remove(channelId);
		}
		BaseChannel oldChannel=ONLINE_CHANNELS.put(playerId, channel);
		if(oldChannel!=null){//踢出已登录的旧客户端连接
			oldChannel.close();
		}
		channel.setAttachment(playerId);
	}
	/**
	 * 移除channel
	 * @param channel
	 */
	public void removeChannel(BaseChannel channel){
		Long playerId=(Long) channel.getAttachment();
		ANONYMOUS_CHANNELS.remove(channel.getId());
		if(playerId!=null){
			ONLINE_CHANNELS.remove(playerId);
		}
	}
	/**
	 * 是否在线
	 * @param playerId
	 * @return
	 */
	public boolean isOnline(long playerId){
		return ONLINE_CHANNELS.contains(playerId);
	}
}
