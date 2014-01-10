package com.tongwan.net;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

public class TcpBootstrap{
	static Log log = LogFactory.getLog(TcpBootstrap.class);
	public static void start() {


		ServerBootstrap tcpBootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));

		tcpBootstrap.setPipelineFactory(new TcpPipelineFactory());
		InetSocketAddress tcp_addr = new InetSocketAddress(8888);
		tcpBootstrap.setOption("child.tcpNoDelay", true);
		tcpBootstrap.bind(tcp_addr);

	}
}
