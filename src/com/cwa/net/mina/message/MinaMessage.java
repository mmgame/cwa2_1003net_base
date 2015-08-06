package com.cwa.net.mina.message;

import org.apache.mina.core.buffer.IoBuffer;

import com.cwa.message.AbstractMessage;
import com.cwa.net.netty.message.NettyMessageType;

/**
 * 消息类型
 * 
 * @author tzy
 * 
 *
 */
public class MinaMessage extends AbstractMessage {
	@Override
	public void readFields(Object d) {
		IoBuffer data = (IoBuffer) d;
		commandId = data.getInt();
		packSize = data.getInt();
		bodyByte = new byte[packSize];
		data.get(bodyByte);
	}

	@Override
	public void write(Object d) {
		IoBuffer data = (IoBuffer) d;
		data.put(NettyMessageType.MARK);
		data.putInt(commandId);
		packSize = bodyByte.length;
		data.putInt(packSize);
		data.put(bodyByte);
	}
}
