package com.cwa.net.netty.filter;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import com.cwa.message.IMessage;
import com.cwa.message.IMessageType;
import com.cwa.net.netty.message.NettyMessageType;

/**
 * 解码消息对象
 * 
 * @author tzy
 * 
 */
public class MessageDecoder extends ByteToMessageDecoder {
	private IMessageType messageType;

	public MessageDecoder(IMessageType messageType) {
		this.messageType = messageType;
	}

	@Override
	protected void decode(ChannelHandlerContext chc, ByteBuf bb, List<Object> list) throws Exception {
		if (bb.readableBytes() < NettyMessageType.HEADER_LEN) {
			// 连消息头都还没收到
			return;
		}
		// 检验标记
		if (!messageType.checkMark(bb)) {
			bb.clear();
			chc.close();
			throw new RuntimeException("协议错误! [Address=" + chc.getClass().toString() + "]");
		}
		if (bb.readableBytes() < NettyMessageType.HEADER_LEN) {
			bb.readerIndex(bb.readerIndex() - NettyMessageType.MARK.length);
			return;
		}
		final long bodyLength = bb.getUnsignedInt(bb.readerIndex() + NettyMessageType.COMMAND_LEN);
		// 消息是否完整到达？
		if (bb.readableBytes() >= NettyMessageType.HEADER_LEN + bodyLength) {
			// 消息完整到达
			IMessage msg = messageType.createMessage();
			msg.readFields(bb);
			list.add(msg);
		} else {
			bb.readerIndex(bb.readerIndex() - NettyMessageType.MARK.length);
		}
	}
}
