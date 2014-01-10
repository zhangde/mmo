package com.tongwan.net;

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

import com.tongwan.module.map.domain.GameMap;
import com.tongwan.module.map.manage.GameMapManage;
import com.tongwan.module.monster.domain.MonsterDomain;

public class TcpHandler extends SimpleChannelHandler {
	static Log log = LogFactory.getLog(TcpHandler.class);
	public static final ChannelGroup group =new DefaultChannelGroup();
	@Override
	public void messageReceived(ChannelHandlerContext ctx,final MessageEvent e) throws Exception {
		log.debug("messageReceived");
		byte[] content=(byte[]) e.getMessage();
		ByteArrayInputStream bais=new ByteArrayInputStream(content);
		ObjectInputStream ois=new ObjectInputStream(bais);
		int cmd=ois.readInt();
		Map map = (Map) ois.readObject();
		dispath(ctx.getChannel(), cmd, map);
	}
	private void dispath(Channel channel,int cmd,Map map){
		switch (cmd) {
		case 1:
			requestGameMap(channel);
			break;

		default:
			break;
		}
	}
	private void requestGameMap(Channel channel){
		GameMap gameMap=GameMapManage.getById(1);
		Map map=new HashMap<>();
		map.put("data", gameMap.getData());
		channel.write(toChannelBuffer(1, map));
	}
	public static void pushAddSprite(MonsterDomain monsterDomain){
		Iterator<Channel> iterators=group.iterator();
		while(iterators.hasNext()){
			Channel channel=iterators.next();
			Map map=new HashMap<>();
			map.put("id", monsterDomain.getId());
			map.put("x", monsterDomain.getX());
			map.put("y", monsterDomain.getY());
			channel.write(toChannelBuffer(2, map));
		}
	}
	private static  ChannelBuffer toChannelBuffer(int cmd,Map parame){
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		
		int length=0;
		try{
			ObjectOutputStream oos=new ObjectOutputStream(baos);
			oos.writeInt(cmd);
			oos.writeObject(parame);
			byte[] bytes=baos.toByteArray();
			ChannelBuffer cb=ChannelBuffers.buffer(4+bytes.length);
			cb.writeInt(bytes.length);
			cb.writeBytes(bytes);
			return cb;
		}catch(Exception e){
			log.error("",e);
		}
		return null;
		
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