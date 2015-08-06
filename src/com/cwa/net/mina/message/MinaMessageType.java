package com.cwa.net.mina.message;

import org.apache.mina.core.buffer.IoBuffer;

import com.cwa.message.IMessage;
import com.cwa.message.IMessageType;

/**
 * 消息类型
 * 
 * @author tzy
 * 
 *
 */
public class MinaMessageType implements IMessageType {
	public final static byte[] MARK = { 'c', 'w', 'a' };
	private final int COMMAND_LEN = 4;
	public final int PACKSIZE_LEN = 4;

	private final int TOTAL_LENGTH = COMMAND_LEN + PACKSIZE_LEN;
	private final int PACKSIZE_POSITION = COMMAND_LEN;

	@Override
	public int getMarkLength() {
		return MARK.length;
	}

	@Override
	public boolean checkMark(Object d) {
		IoBuffer data = (IoBuffer) d;
		// 验证指令mark
		byte[] cmark = new byte[MARK.length];
		data.get(cmark);
		for (int i = 0; i < MARK.length; i++) {
			if (cmark[i] != MARK[i]) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int getHeaderLen() {
		return TOTAL_LENGTH;
	}

	@Override
	public int getPackSizePosition() {
		return PACKSIZE_POSITION;
	}

	@Override
	public int getPackSize() {
		return PACKSIZE_LEN;
	}

	@Override
	public IMessage createMessage() {
		return new MinaMessage();
	}
}
