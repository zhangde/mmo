package com.tongwan.net;


import static org.jboss.netty.channel.Channels.pipeline;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tongwan.common.net.encoder.netty.SimpleDecoder;


@Component
public class TcpPipelineFactory implements ChannelPipelineFactory {
	@Autowired
	private TcpHandler tcpHandler;
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = pipeline();
		pipeline.addLast("decoder", new SimpleDecoder());
		pipeline.addLast("tcp", tcpHandler);
		return pipeline;
	}
}
