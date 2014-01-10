package com.tongwan.net;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class B2Decoder extends FrameDecoder{
	static Log log=LogFactory.getLog(B2Decoder.class);
	@Override
	protected Object decode(ChannelHandlerContext arg0, Channel arg1,
			ChannelBuffer buffer) throws Exception {
		try{
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
		}catch(Exception e){
			buffer.resetReaderIndex();
			byte[] bytes= buffer.array();
			log.error(new String(bytes),e);
		}
		arg1.close();
		return null;
	}

}
