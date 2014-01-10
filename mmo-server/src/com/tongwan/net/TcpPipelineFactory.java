package com.tongwan.net;


import static org.jboss.netty.channel.Channels.pipeline;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;



public class TcpPipelineFactory implements ChannelPipelineFactory {
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = pipeline();
		pipeline.addLast("decoder", new B2Decoder());
		pipeline.addLast("tcp", new TcpHandler());
		return pipeline;
	}
}
