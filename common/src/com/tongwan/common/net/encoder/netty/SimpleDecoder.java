package com.tongwan.common.net.encoder.netty;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import com.tongwan.common.io.rpc.RpcInput;
import com.tongwan.common.io.rpc.impl.RpcInputNettyImpl;
/**
 * 简单解码器
 * @author zhangde
 *
 * @date 2014年1月10日
 */
public class SimpleDecoder extends FrameDecoder{
	
	@Override
	protected Object decode(ChannelHandlerContext arg0, Channel arg1,
			ChannelBuffer buffer) throws Exception {
		int readableBytes=buffer.readableBytes();
		if(readableBytes<4){
			return null;
		}
		buffer.markReaderIndex();
		int needBytes = buffer.readInt();
		if(buffer.readableBytes()<needBytes){
			buffer.resetReaderIndex();
			return null;
		}
		ChannelBuffer result=buffer.slice(4, needBytes);
		buffer.skipBytes(needBytes);
		RpcInput rpcInput = new RpcInputNettyImpl(result);
		return rpcInput;
	}

}
