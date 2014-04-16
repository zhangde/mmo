package com.tongwan.common.net.channel.netty;

import java.nio.ByteOrder;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;

import com.tongwan.common.io.rpc.RpcOutput;
import com.tongwan.common.io.rpc.impl.RpcOutputNettyImpl;
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
		try{
			RpcOutput out=new RpcOutputNettyImpl();
			out.writeInt(resultObject.getCmd());
			out.writeInt(resultObject.getResult());
			out.writeObject(resultObject.getValue());
			
			byte[] bytes=out.toByteArray();
			
			ChannelBuffer buffer= ChannelBuffers.buffer(ByteOrder.LITTLE_ENDIAN, 4+bytes.length);
			buffer.writeInt(bytes.length);
			buffer.writeBytes(bytes);
			channel.write(buffer);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	@Override
	public void setAttachment(Object attachment) {
		channel.setAttachment(attachment);
	}

	@Override
	public Object getAttachment() {
		return channel.getAttachment();
	}

	@Override
	public int getId() {
		return channel.getId();
	}

	
	@Override
	public void close() {
		channel.close();
	}

}
