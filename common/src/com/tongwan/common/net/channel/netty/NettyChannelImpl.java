package com.tongwan.common.net.channel.netty;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;

import com.tongwan.common.builder.rpc.RpcVo;
import com.tongwan.common.builder.rpc.io.RpcOutput;
import com.tongwan.common.net.ResultObject;
import com.tongwan.common.net.channel.BaseChannel;

/**
 * @author zhangde
 *
 * @date 2014年1月21日
 */
public class NettyChannelImpl implements BaseChannel {
	private Channel channel;
	public NettyChannelImpl(Channel channel){
		this.channel=channel;
	}
	
	@Override
	public void writeRpcOutput(RpcOutput out) {
		byte[] bytes=out.toByteArray();
		ChannelBuffer buffer=ChannelBuffers.buffer(4+bytes.length);
		buffer.writeInt(bytes.length);
		buffer.writeBytes(bytes);
		channel.write(buffer);
	}

	@Override
	public  void writeResultObject(ResultObject resultObject) {
		RpcOutput out=new RpcOutput();
		out.writeInt(resultObject.getCmd());
		out.writeInt(resultObject.getResult());
		out.writeObject(resultObject.getValue());
		
		byte[] bytes=out.toByteArray();
		ChannelBuffer buffer=ChannelBuffers.buffer(4+bytes.length);
		buffer.writeInt(bytes.length);
		buffer.writeBytes(bytes);
		channel.write(buffer);
	}

}
