package com.tongwan.net;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
/**
 * 
 * @author zhangde
 *
 * @date 2014Äê1ÔÂ9ÈÕ
 */
public class DefaultDecoder extends FrameDecoder{

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
		byte[] content = new byte[needBytes];
		buffer.readBytes(content);
		return content;
	}

}
