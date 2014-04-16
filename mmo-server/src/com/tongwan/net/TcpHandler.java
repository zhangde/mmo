package com.tongwan.net;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tongwan.common.io.rpc.RpcInput;
import com.tongwan.common.net.channel.netty.NettyChannelImpl;
import com.tongwan.manage.SessionManage;
@Component
public class TcpHandler extends SimpleChannelHandler {
	static Log log = LogFactory.getLog(TcpHandler.class);
	public static final ChannelGroup group =new DefaultChannelGroup();
	@Autowired
	private ModuleDispatcher dispatcher;
	@Autowired
	private SessionManage sessionManage;
	@Override
	public void messageReceived(ChannelHandlerContext ctx,final MessageEvent e) throws Exception {
		log.debug("messageReceived");
		dispatcher.dispatch(new NettyChannelImpl(e.getChannel()), (RpcInput)e.getMessage());
	}
	
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		log.debug("channelClosed");
		
		long playerId=(long) e.getChannel().getAttachment();
		//sessionManage.removeChannel(e.getChannel());
	}
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		log.debug("channelConnected");
		sessionManage.putToOnlineList(e.getChannel().getId(), new NettyChannelImpl(e.getChannel()));
		
	}
	
}