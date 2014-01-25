package com.tongwan.net;

import gen.service.RpcService;

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

import com.tongwan.common.builder.rpc.io.RpcInput;
import com.tongwan.common.net.channel.netty.NettyChannelImpl;
@Component
public class TcpHandler extends SimpleChannelHandler {
	static Log log = LogFactory.getLog(TcpHandler.class);
	public static final ChannelGroup group =new DefaultChannelGroup();
	@Autowired
	private RpcService service;
	@Override
	public void messageReceived(ChannelHandlerContext ctx,final MessageEvent e) throws Exception {
		log.debug("messageReceived");
		service.process(new NettyChannelImpl(e.getChannel()), new RpcInput((byte[])e.getMessage()));
	}
	
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		log.debug("channelClosed");
	}
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		log.debug("channelConnected");
		group.add(ctx.getChannel());
	}
	
}