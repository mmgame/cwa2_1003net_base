package com.cwa.net.mina.filter;

import org.apache.mina.core.buffer.CachedBufferAllocator;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.cwa.net.mina.message.MinaMessage;

/**
 * 将要发送的信息编码
 * 
 * @author mali
 */
public class MessageSendEncoder extends ProtocolEncoderAdapter {

	private CachedBufferAllocator allocator = new CachedBufferAllocator();

	@Override
	public void encode(IoSession session, Object obj, ProtocolEncoderOutput out) throws Exception {
		MinaMessage message=(MinaMessage) obj;
		IoBuffer buffer = getIoBuffer();
		message.write(buffer);

		/** ********* 协议头封装完毕 *********** */
		buffer.flip();

		out.write(buffer);
	}

	private IoBuffer getIoBuffer() {
		IoBuffer buffer = allocator.allocate(128, false);
		buffer.setAutoExpand(true);
		buffer.setAutoShrink(true);
		return buffer;
	}
}
