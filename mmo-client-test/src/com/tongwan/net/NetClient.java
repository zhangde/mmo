package com.tongwan.net;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import com.tongwan.MainJFrame;

public class NetClient {
	static Log log=LogFactory.getLog(NetClient.class);
	static ClientBootstrap bootstrap;
	public static void init(final MainJFrame frame){
		Executor bossExecutor=Executors.newCachedThreadPool();
		Executor workerExecutor=Executors.newCachedThreadPool();
		ChannelFactory factory=new NioClientSocketChannelFactory(bossExecutor, workerExecutor);
		bootstrap=new ClientBootstrap(factory);
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = Channels.pipeline();
                pipeline.addLast("decoder", new DefaultDecoder());
                pipeline.addLast("handler", new NetClientHandler(frame));
                return pipeline;
            }
        });
	}
	public static ChannelFuture conn(){
		//log.debug("begin connection gate");
		return bootstrap.connect(new InetSocketAddress("127.0.0.1", 8888));
	}
	public static void main(String[] args) throws Exception{
		NetClient.init(null);

		ChannelFuture cf=NetClient.conn();
//		cf.awaitUninterruptibly(10000);
		
//		GateClientHandler.service.register(AppContext.SERVERID());
	}
	
}
