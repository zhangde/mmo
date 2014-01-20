package com.tongwan.net;

import gen.service.RpcService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
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
import com.tongwan.domain.map.GameMap;
import com.tongwan.domain.monster.MonsterDomain;
import com.tongwan.manage.GameMapManage;
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
//		byte[] content=(byte[]) e.getMessage();
//		ByteArrayInputStream bais=new ByteArrayInputStream(content);
//		ObjectInputStream ois=new ObjectInputStream(bais);
//		int cmd=ois.readInt();
//		Map map = (Map) ois.readObject();
//		dispath(ctx.getChannel(), cmd, map);
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