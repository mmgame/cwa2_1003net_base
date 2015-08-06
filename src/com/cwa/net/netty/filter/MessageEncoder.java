package com.cwa.net.netty.filter;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import com.cwa.net.netty.message.NettyMessage;

/**
 * 编码消息对象
 * @author tzy
 *
 */

public class MessageEncoder extends MessageToByteEncoder<Object> {
	@Override
	protected void encode(ChannelHandlerContext chc, Object msg, ByteBuf bb) throws Exception {
		
		NettyMessage message=(NettyMessage) msg;
		message.write(bb);
	    
	}
}
