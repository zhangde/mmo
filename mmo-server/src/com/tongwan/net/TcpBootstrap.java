package com.tongwan.net;

import java.net.InetSocketAddress;
import java.nio.ByteOrder;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.HeapChannelBufferFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class TcpBootstrap{
	static Log log = LogFactory.getLog(TcpBootstrap.class);
	@Autowired
	private TcpPipelineFactory tcpPipelineFactory;
	public void start() {


		ServerBootstrap tcpBootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));

		tcpBootstrap.setPipelineFactory(tcpPipelineFactory);
		InetSocketAddress tcp_addr = new InetSocketAddress(8888);
		tcpBootstrap.setOption("child.tcpNoDelay", true);
		tcpBootstrap.setOption("child.bufferFactory", new HeapChannelBufferFactory(ByteOrder.LITTLE_ENDIAN));
		tcpBootstrap.bind(tcp_addr);

	}
}
