package com.tongwan.net;


import gen.client.RpcClient;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import com.tongwan.MainJFrame;
import com.tongwan.MainJPanel;
import com.tongwan.common.builder.rpc.io.RpcInput;
import com.tongwan.common.net.channel.netty.NettyChannelImpl;


public class NetClientHandler extends SimpleChannelUpstreamHandler{
	static Log log=LogFactory.getLog(NetClientHandler.class);
	static ScheduledExecutorService service=Executors.newSingleThreadScheduledExecutor();
	private MainJFrame frame;
	private RpcClient client;
	public NetClientHandler(MainJFrame frame){
		super();
		this.frame=frame;
	}
	@Override
	public void channelConnected(ChannelHandlerContext ctx,final ChannelStateEvent e) throws Exception {
		log.debug("server connect!");
		client=new RpcClientImpl(new NettyChannelImpl(e.getChannel()));
		client.loadGameMap();
	}
	
	

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		log.debug("channelClosed after 10 seconds try connect");
		tryConnect();
	}
	public static void tryConnect(){
		service.schedule(new Runnable() {
			@Override
			public void run() {
				ChannelFuture cf=NetClient.conn();
				cf.awaitUninterruptibly();
			}
		}, 10, TimeUnit.SECONDS);
	}
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) throws Exception {
		log.debug("channelDisconnected");
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		log.debug("messageReceived");
		byte[] content=(byte[]) e.getMessage();
		client.dispath(new RpcInput(content));
//		ByteArrayInputStream bais=new ByteArrayInputStream(content);
//		ObjectInputStream ois=new ObjectInputStream(bais);
//		int cmd=ois.readInt();
//		Map map = (Map) ois.readObject();
//		dispath(ctx.getChannel(), cmd, map);
	}
	private void dispath(Channel channel,int cmd,Map map){
		switch (cmd) {
		case 1:
			receiveGameMap(map);
			break;
		case 2:
			receivePushAddSprite(map);
			break;
		default:
			break;
		}
	}
	private void requestGameMap(Channel channel){
		Map map=new HashMap<>();
		ChannelBuffer buffer=toChannelBuffer(1, map);
		channel.write(buffer);
	}
	private void receiveGameMap(Map map){
		byte[][] data=(byte[][]) map.get("data");
		MainJPanel panel= frame.getPanel();
		panel.setMapData(data);
		panel.repaint();
	}
	private Map<Long,Map> monsteres=new HashMap<>();
	private void receivePushAddSprite(Map map){
		long id =(long) map.get("id");
		monsteres.put(id, map);
		frame.getPanel().setMonster(monsteres);
	}
	private ChannelBuffer toChannelBuffer(int cmd,Map parame){
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
}
