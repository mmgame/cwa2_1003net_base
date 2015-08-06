package com.cwa.net.netty.message;

import io.netty.buffer.ByteBuf;

import com.cwa.message.IMessage;
import com.cwa.message.IMessageType;

/**
 *
 * 消息类型
 */
public class NettyMessageType implements IMessageType {
	// 标志：cwa
	public static final byte[] MARK = { 'c', 'w', 'a' };
	// 消息ID
	public static final int COMMAND_LEN = 4;
	// 包体长度
	public static final int PACKSIZE_LEN = 4;
	// Protobuf
	public static final int HEADER_LEN = COMMAND_LEN + PACKSIZE_LEN;

	@Override
	public int getMarkLength() {
		return MARK.length;
	}

	@Override
	public boolean checkMark(Object d) {
		ByteBuf data = (ByteBuf) d;
		// 验证指令mark
		byte[] cmark = new byte[MARK.length];
		data.readBytes(cmark);
		for (int i = 0; i < MARK.length; i++) {
			if (cmark[i] != MARK[i]) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int getHeaderLen() {
		return HEADER_LEN;
	}

	@Override
	public int getPackSizePosition() {
		return COMMAND_LEN;
	}

	@Override
	public int getPackSize() {
		return PACKSIZE_LEN;
	}

	@Override
	public IMessage createMessage() {
		return new NettyMessage();
	}
}
